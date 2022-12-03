package com.cshutchinson.adventofcode2022

class Day01 : Solution {
    override fun solve (input: Sequence<String>) {
        val sumOfCaloriesPerElf = input
            .map { x -> if (x.isEmpty()) 0 else x.toInt() }
            .split { x -> x == 0 }
            .map { x -> x.sum() }
            .sorted()
            .toList()

        println(first(sumOfCaloriesPerElf))
        println(second(sumOfCaloriesPerElf))
    }

    fun first (input: Iterable<Int>) = input.maxOrNull()
    fun second (input: Iterable<Int>) = input.reversed().take(3).sum()
}