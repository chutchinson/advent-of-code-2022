class Day12 : Solver {

    data class Vector2i(val x: Int, val y: Int)
    
    data class Grid(val cells: List<Char>,
                    val width: Int, val height: Int)
    
    override fun solve (input: Sequence<String>) {
        val grid = parse(input)
        println(first(grid))
        println(second(grid))
    }    
    
    fun first (grid: Grid): Int {
        val start = grid.points().find { grid.get(it.x, it.y, false) == 'S' }
        val end = grid.points().find { grid.get(it.x, it.y, false) == 'E' }
        return grid.climb(start!!, end!!)   
    }
    
    fun second (grid: Grid): Int {
        val end = grid.points().find { grid.get(it.x, it.y, false) == 'E' }
        return grid.points()
            .filter { grid.get(it.x, it.y) == 'a' }
            .map { grid.climb(it, end!!) }
            .filter { it != 0 }
            .min()   
    }
    
    fun parse (input: Sequence<String>): Grid {
        val cells = mutableListOf<Char>()
        val lines = input.toList()
        var width = 0
        var height = 0
        for (line in lines) {
            for (ch in line) {
                cells.add(ch)
            }
            width = line.length
            height += 1
        }
        return Grid(cells, width, height)
    }
    
    fun Grid.get (x: Int, y: Int, reduce: Boolean = true): Char {
        val value = cells[y * width + x]
        if (reduce && value == 'S') return 'a'
        if (reduce && value == 'E') return 'z'
        return value
    } 
    
    fun Grid.points () = sequence {
        for (y in 0 until height) {
            for (x in 0 until width) {
                yield(Vector2i(x, y))
            }
        }
    }

    fun Grid.neighbors (x: Int, y: Int) = sequence {
        fun valid (nx: Int, ny: Int): Boolean {
            if (nx < 0 || nx >= width) return false
            if (ny < 0 || ny >= height) return false
            return get(nx, ny) <= get(x, y) + 1
        }
        if (valid(x - 1, y)) yield(Vector2i(x - 1, y))
        if (valid(x + 1, y)) yield(Vector2i(x + 1, y))
        if (valid(x, y - 1)) yield(Vector2i(x, y - 1))
        if (valid(x, y + 1)) yield(Vector2i(x, y + 1))
    }

    fun Grid.climb (start: Vector2i, end: Vector2i): Int {
        val queue = ArrayDeque<Vector2i>()
        val scores = mutableMapOf<Vector2i, Int>()
        val visited = mutableSetOf<Vector2i>() // no backtracking
        
        fun visit (current: Vector2i, next: Vector2i) {
            scores[next] = (scores[current] ?: -1) + 1
            queue.addLast(next)
            visited.add(next)
        }
        
        visit(start, start)
        
        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (current == end) {
                return scores[end]!!
            }
            for (neighbor in neighbors(current.x, current.y)) {
                if (!visited.contains(neighbor)) {
                    visit(current, neighbor)
                }
            }
        }

        return 0
    }
}