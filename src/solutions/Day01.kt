package com.cshutchinson.adventofcode2022

class Day01 : Solver {
    override fun solve (input: Sequence<String>) {
        val sumOfCaloriesPerElf = input
            .split { it.isEmpty() }
            .map { it.map { it.toInt() }}
            .map { it.sum() }
            .sortedDescending()
            .toList()

        println(first(sumOfCaloriesPerElf))
        println(second(sumOfCaloriesPerElf))
    }

    fun first (input: Iterable<Int>) = input.first()
    fun second (input: Iterable<Int>) = input.take(3).sum()
}