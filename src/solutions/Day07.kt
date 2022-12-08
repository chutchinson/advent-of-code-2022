package com.cshutchinson.adventofcode2022

class Day07 : Solver {
    
    open class Node (val parent: Node?, val children: MutableList<Node>) {
        open val size: Int get() = 0
        
        fun iterator (): Sequence<Node> {
            val stack = ArrayDeque<Node>()
            stack.addLast(this)
            return sequence {
                while (stack.size > 0) {
                    val node = stack.removeFirst()
                    for (child in node.children) stack.addLast(child)
                    yield (node)
                }
            }
        }
    }

    class Directory (parent: Node?, val name: String) : Node(parent, mutableListOf()) {
        override val size: Int get() = this.iterator()
            .filter { it is File }
            .map { it.size }
            .sum()
    }

    class File (parent: Node?, val name: String, val fileSize: Int): Node(parent, mutableListOf()) {
        override val size : Int get() = fileSize
    }
    
    override fun solve (input: Sequence<String>) {
        val tree = parse(input)
        println(first(tree))
        println(second(tree))
    }

    fun first (tree: Directory): Int {
        return tree.iterator()
            .filter { it is Directory && it.size < 100000 }
            .sumOf { it.size }
    }

    fun second (tree: Directory): Int {
        val totalUnused = 70000000 - tree.iterator()
            .filter { it is File }
            .sumOf { it.size }
        return tree.iterator()
            .filter { it is Directory && totalUnused + it.size >= 30000000 }
            .minOf { it.size }
    }
    
    fun parse (input: Sequence<String>) : Directory {
        val root = Directory(null, "/")
        var current: Node? = root
        
        for (line in input) {
            val cmd = line.substring(0, 4)
            val arg = if (line.length >= 5) line.substring(5) else ""
            when {
                cmd == "dir " -> continue
                cmd == "$ ls" -> continue
                cmd == "$ cd" && arg == "/"  -> current = root
                cmd == "$ cd" && arg == ".." -> current = current?.parent
                cmd == "$ cd" -> {
                    val parent = current
                    current = Directory(current, arg)
                    parent?.children?.add(current)
                }
                else -> {
                    val parts = line.split(" ")
                    val size = parts[0].toInt()
                    val name = parts[1]
                    current?.children?.add(File(current, name, size))
                }
            }
        }
        
        return root
    }
}