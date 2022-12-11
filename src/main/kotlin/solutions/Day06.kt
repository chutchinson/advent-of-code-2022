class Day06 : Solver {
    override fun solve (input: Sequence<String>) {
        val datastream = input.joinToString("")
        println(first(datastream))
        println(second(datastream))
    }
    
    fun findMarker (input: String, size: Int): Int {
        for (index in 0..input.length) {
            val slice = input.substring(index, index + size)
            if (slice.toSet().size == size) {
                return index + size
            }
        }
        return -1
    }

    fun first (input: String) = findMarker(input, 4)
    fun second (input: String) = findMarker(input, 14)
}