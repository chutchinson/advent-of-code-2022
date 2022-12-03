package com.cshutchinson.adventofcode2022

interface Solution {
    fun solve(input: Sequence<String>)
}

class App {
    companion object {
        val solutions = listOf(
            Day01(), Day02()
        )

        @JvmStatic
        fun main (args: Array<String>) {
            val day = args[0].toInt()
            val solution = solutions[day - 1]
            solution.solve(generateSequence(::readLine))
        }
    }
}