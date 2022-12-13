import java.io.File

interface Solver {
    fun solve(input: Sequence<String>)
}

val solvers = listOf(
    Day01(), Day02(), Day03(), Day04(), Day05(), Day06(),
    Day07(), Day08(), Day09(), Day10(), Day11(), Day12()
)

fun main (args: Array<String>) {
    val day = args[0].toInt()
    val solver = solvers[day - 1]
    val input = File(String.format("inputs/%d.txt", day))
    solver.solve(input.readLines().asSequence())
}