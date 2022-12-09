package com.cshutchinson.adventofcode2022

import kotlin.math.*

class Day09 : Solver {
    data class Vector2(var x: Int, var y: Int)
    data class Move(val direction: Char, val distance: Int)
    
    override fun solve (input: Sequence<String>) {
        fun parse (v: String) = Move(v[0], v.substring(2).toInt())
        val moves = input.map(::parse).toList()
        println(first(moves))
        println(second(moves))
    }

    fun first (input: Iterable<Move>) = simulate(input, 2)
    fun second (input: Iterable<Move>) = simulate(input, 10)
    
    fun simulate (moves: Iterable<Move>, n: Int): Int {
        val knots = (0 until n).map { Vector2(0, 0) }.toList()
        val visited = mutableSetOf<Vector2>()
        
        fun move (dx: Int, dy: Int) {
            fun gap (a: Int, b: Int) = (a - b).absoluteValue >= 2
            fun diaganol (a: Int, b: Int) = if (a != b) { (a - b).sign } else { 0 }

            val head = knots[0]
            val tail = knots[knots.size - 1]
            
            head.x += dx
            head.y += dy

            for (k in 0 until knots.size - 1) {
                val knot = knots[k]
                val next = knots[k + 1]
                if (gap(knot.x, next.x)) {
                    next.x += (knot.x - next.x).sign
                    next.y += diaganol(knot.y, next.y)
                }
                if (gap(knot.y, next.y)) {
                    next.y += (knot.y - next.y).sign
                    next.x += diaganol(knot.x, next.x)
                }
            }

            visited.add(Vector2(tail.x, tail.y))
        }

        for (m in moves) {
            for (x in 0 until m.distance) {
                when (m.direction) {
                    'L' -> move(-1, 0)
                    'R' -> move(1, 0)
                    'U' -> move(0, 1)
                    'D' -> move(0, -1)
                }
            }
        }

        return visited.size
    }
    
}
