class Day03 : Solver {
    override fun solve (input: Sequence<String>) {
        val rucksacks = input.toList()
        println(first(rucksacks))
        println(second(rucksacks))
    }

    fun priority (ch: Char): Int = when(ch) {
        in 'A'..'Z' -> ch.code - 64 + 26
        in 'a'..'z' -> ch.code - 96
        else -> 0
    }

    fun first (rucksacks: Iterable<String>) = rucksacks
        // build compartments
        .map { it.chunked(it.length / 2) }
        // find intersection of letters in first compartment with second compartment
        .map { it[0].toSet() intersect it[1].toSet() }
        .map { it.first() }
        // calculate priorities and sum
        .map(::priority)
        .sum()

    fun second (rucksacks: Iterable<String>) = rucksacks
        // select groups of 3 rucksacks
        .chunked(3)
        // find intersection of all 3 rucksacks in each group
        .map { chunk -> chunk.map { it.toSet() }}
        .map { chunk -> chunk.reduce { all, set -> all intersect set }}
        .flatten()
        // calculate priorities and sum
        .map(::priority)
        .sum()
}