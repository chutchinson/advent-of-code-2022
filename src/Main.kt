package com.cshutchinson.adventofcode2022

interface Solver {
    fun solve(input: Sequence<String>)
}

class App {
    companion object {
        val solvers = listOf(
            Day01(), Day02(), Day03(), Day04(), Day05(), Day06()
        )

        @JvmStatic
        fun main (args: Array<String>) {
            val day = args[0].toInt()
            var solver = solvers[day - 1]
            solver.solve(generateSequence(::readLine))
        }
    }
}