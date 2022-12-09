package com.cshutchinson.adventofcode2022

typealias Ray = Sequence<Int>

class Day08 : Solver {
    
    override fun solve (input: Sequence<String>) {
        val heights = input.map { it.map { it.digitToInt() }}.flatten().toList()
        val grid = Grid(heights)
        println(first(grid))
        println(second(grid))
    }

    fun first (grid: Grid): Int {
        return grid.map { x, y -> grid
            .scan(x, y).any { it.all { it < grid.height(x, y) }}
        }
        .filter { it }
        .count()
    }
    
    fun second (grid: Grid): Int {
        return grid.map { x, y -> grid
            .scan(x, y)
            .map { it.takeUntil { it >= grid.height(x, y)}.count() }
            .reduce { score, h -> score * h }
        }
        .maxOf { it }
    }
}

class Grid {
    val field: List<Int>
    val size: Int
    
    constructor (field: List<Int>) {
        this.field = field
        this.size = Math.sqrt(field.size.toDouble()).toInt()
    }
    
    fun height (x: Int, y: Int): Int = field[y * size + x]
    
    fun raycast (fromX: Int, fromY: Int, toX: Int, toY: Int, dx: Int, dy: Int): Ray {
        return sequence {
            var x = fromX
            var y = fromY
            while (x != toX || y != toY) {
                yield(height(x, y))
                x += dx
                y += dy
            }
        }
    }
    
    fun <T> map (fn: (x: Int, y: Int) -> T): Sequence<T> = sequence {
        for (y in 0..size-1) {
            for (x in 0..size-1) {
                yield(fn(x, y))
            }
        }
    }
    
    fun scan (x: Int, y: Int) = sequence {
        yield(raycast(x, y - 1, x, -1, 0, -1))   // up
        yield(raycast(x - 1, y, -1, y, -1, 0))   // left
        yield(raycast(x, y + 1, x, size, 0, 1))  // down
        yield(raycast(x + 1, y, size, y, 1, 0))  // right
    }
}