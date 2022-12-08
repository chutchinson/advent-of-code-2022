package com.cshutchinson.adventofcode2022

fun <T> Sequence<T>.split (predicate: (T) -> Boolean): Sequence<List<T>> {
    return sequence {
        var collection = mutableListOf<T>()
        for (x in iterator()) {
            if (!predicate(x)) collection.add(x)
            else {
                yield(collection)
                collection = mutableListOf<T>()
            }
        }
    }
}

fun <T> Sequence<T>.takeUntil (predicate: (T) -> Boolean): Sequence<T> {
    return sequence {
        with (iterator()) {
            while (hasNext()) {
                val next = next()
                yield (next)
                if (predicate(next)) break
            }
        }
    }
}