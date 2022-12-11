class Day11 : Solver {
    
    data class Monkey(
        val items: ArrayDeque<Long>,
        val worry: (Long) -> Long,
        val divisor: Long,
        val throwTrue: Int,
        val throwFalse: Int,
        var inspections: Long)

    override fun solve (input: Sequence<String>) {
        val monkies = input
            .split { it.isEmpty() }
            .map(::toMonkey)
            .toList()
        
        println(first(monkies))
        println(second(monkies))
    }
    
    fun first (input: List<Monkey>) = monkeyBusiness(input, 20) { it / 3 }

    fun second (input: List<Monkey>): Long {
        // this took me a while; we cannot trivially parallelize nor can we use arbitrary precision because the scale of the
        // numbers grows too rapidly, so it was pretty clear right away we need modular artihmetic, but the problem is determining
        // the modulus; the least common multiple (LCM), aka least common divisor is perfect here and it's easy to compute because
        // all of the divisors happen to be distinct prime factors (at least in my input)
        val modulus = input.fold(1L) { lcm, monkey -> lcm * monkey.divisor }
        return monkeyBusiness(input, 10000) { it % modulus }
    }
    
    private fun toMonkey (input: List<String>): Monkey {
        val startingItems = ArrayDeque(input[1].substring(18).split(",").map { it.trim().toLong() })
        val op = input[2].substring(23, 24)
        val value = input[2].substring(25)
        val divisor = input[3].substring(21).toLong()
        val ifTrue = input[4].substring(29).toInt()
        val ifFalse = input[5].substring(30).toInt()
        val func: (Long) -> Long = when {
            op == "+" && value != "old" -> { x -> x + value.toLong() }
            op == "*" && value != "old" -> { x -> x * value.toLong() }
            else -> {x -> x * x }
        }

        return Monkey(startingItems, func, divisor, ifTrue, ifFalse, 0)
    }

    private fun monkeyBusiness (monkies: List<Monkey>, n: Int, strategy: (Long) -> Long): Long {
        fun round (monkey: Monkey) {
            while (!monkey.items.isEmpty()) {
                val item = monkey.items.removeFirst()
                val worry = strategy(monkey.worry(item))
                val target = if (worry % monkey.divisor == 0L) monkey.throwTrue else monkey.throwFalse
                monkies[target].items.addLast(worry)
                monkey.inspections += 1
            }
        }

        for (round in 0 until n) {
            for (monkey in monkies) {
                round(monkey)
            }
        }

        return monkies
            .map { it.inspections }
            .sortedByDescending { it }
            .take(2)
            .reduce { acc, count -> acc * count }
    }
}