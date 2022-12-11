typealias Action = Triple<Int, Int, Int>
typealias Stack = ArrayDeque<Char>

class Day05 : Solver {

    data class Puzzle(
        val stacks: List<Stack>,
        val actions: List<Action>)

    fun Puzzle.move (multiple: Boolean): Puzzle {
        for (action in this.actions) {
            val (count, from, to) = action
            val crates = ArrayDeque<Char>()
            for (x in 0..count-1) {
                val crate = this.stacks[from - 1].removeFirst()
                if (multiple) crates.addFirst(crate)
                else crates.addLast(crate)
            }
            while (crates.size > 0) {
                val crate = crates.removeFirst()
                this.stacks[to - 1].addFirst(crate)
            }
        }
        return this
    }
    
    fun Puzzle.display (): String {
        return this.stacks.map { it.first() }.joinToString("")
    }
    
    fun parse (input: List<String>): Puzzle {
        val stacks = mutableListOf<Stack>()
        val actions = mutableListOf<Action>()
        var state = 0
        for (line in input) {
            if (line.isEmpty()) {
                state = 1
                continue
            }
            if (state == 0) {
                val pattern = Regex("\\[(.)\\]+")
                val matches = pattern.findAll(line).map { it.groups[1] }.toList()
                for (group in matches) {
                    val index = (group!!.range.first - 1) / 4
                    while (stacks.size <= index) stacks.add(ArrayDeque())
                    stacks[index].add(group.value[0])
                }
            }
            if (state == 1) {
                val pattern = Regex("move (\\d+) from (\\d+) to (\\d+)")
                val rmatch = pattern.find(line)
                val (a, b, c) = rmatch!!.destructured
                actions.add(Action(a.toInt(), b.toInt(), c.toInt()))
            }
        }
        return Puzzle(stacks, actions)
    }

    override fun solve (input: Sequence<String>) {
        val lines = input.toList()
        println(first(parse(lines)))
        println(second(parse(lines)))
    }

    fun first (input: Puzzle): String = input.move(false).display()
    fun second (input: Puzzle): String = input.move(true).display()
}