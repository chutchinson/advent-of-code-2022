package com.cshutchinson.adventofcode2022

class Day02 : Solver {
    override fun solve (input: Sequence<String>) {
        val actions = input.toList()
        println(actions.map(::first).sum())
        println(actions.map(::second).sum())
    }

    fun first (action: String): Int = when(action) {
        "A X" -> ROCK + DRAW
        "A Y" -> PAPER + WIN
        "A Z" -> SCISS + LOSS
        "B X" -> ROCK + LOSS
        "B Y" -> PAPER + DRAW
        "B Z" -> SCISS + WIN
        "C X" -> ROCK + WIN
        "C Y" -> PAPER + LOSS
        "C Z" -> SCISS + DRAW
        else -> error("invalid input")
    }

    fun second (action: String): Int = when(action) {
        "A X" -> SCISS + LOSS
        "A Y" -> ROCK + DRAW
        "A Z" -> PAPER + WIN
        "B X" -> ROCK + LOSS
        "B Y" -> PAPER + DRAW
        "B Z" -> SCISS + WIN
        "C X" -> PAPER + LOSS
        "C Y" -> SCISS + DRAW
        "C Z" -> ROCK + WIN
        else -> error("invalid input")
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