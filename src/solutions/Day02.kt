package com.cshutchinson.adventofcode2022

typealias Action = Pair<Char, Char>

class Day02 : Solution {
    override fun solve (input: Sequence<String>) {
        val actions = input.map { x -> x[0] to x[2] }.toList()
        println(actions.map(::first).sum())
        println(actions.map(::second).sum())
    }

    fun first (action: Action): Int = when(action) {
        'A' to 'X' -> ROCK + DRAW
        'A' to 'Y' -> PAPER + WIN
        'A' to 'Z' -> SCISS + LOSS
        'B' to 'X' -> ROCK + LOSS
        'B' to 'Y' -> PAPER + DRAW
        'B' to 'Z' -> SCISS + WIN
        'C' to 'X' -> ROCK + WIN
        'C' to 'Y' -> PAPER + LOSS
        'C' to 'Z' -> SCISS + DRAW
        else -> 0
    }

    fun second (action: Action): Int = when(action) {
        'A' to 'X' -> SCISS + LOSS
        'A' to 'Y' -> ROCK + DRAW
        'A' to 'Z' -> PAPER + WIN
        'B' to 'X' -> ROCK + LOSS
        'B' to 'Y' -> PAPER + DRAW
        'B' to 'Z' -> SCISS + WIN
        'C' to 'X' -> PAPER + LOSS
        'C' to 'Y' -> SCISS + DRAW
        'C' to 'Z' -> ROCK + WIN
        else -> 0
    }

    companion object {
        val WIN = 6
        val DRAW = 3
        val LOSS = 0
        val ROCK = 1
        val PAPER = 2
        val SCISS = 3
    }
}