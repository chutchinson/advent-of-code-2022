package com.cshutchinson.adventofcode2022

class Day04 : Solver {
    override fun solve (input: Sequence<String>) {
        fun parse (line: String) =
            line.split("-").map { it.toInt() }

        val sections = input
            .map { it.split(",").map(::parse) }
            .map { it.map { (it[0]..it[1]).toSet() }}
            .toList()

        println(first(sections))
        println(second(sections))
    }

    fun first (input: List<List<Set<Int>>>) = input.count { it[0].containsAll(it[1]) || it[1].containsAll(it[0]) }
    fun second (input: List<List<Set<Int>>>) = input.count { it[0].intersect(it[1]).isEmpty() == false }
}