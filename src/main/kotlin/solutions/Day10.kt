enum class Opcode {
    NOOP,
    ADDX
}

data class Instruction(val op: Opcode, val arg1: Int)

class Cpu {
    var x: Int = 1
    var pc: Int = 0
    var cycles: Int = 1
    var stage: Int = 0
    var rom: List<Instruction>
    var halt = false
    
    constructor (rom: List<Instruction>) {
        this.rom = rom
    }
    
    fun cycle () {
        val instruction = rom[pc]
        when (instruction.op) {
            Opcode.NOOP -> noop()
            Opcode.ADDX -> addx(instruction.arg1)
        }
        cycles += 1
        halt = pc >= rom.size
    }
    
    fun noop () {
        pc += 1
    }
    
    fun addx (v: Int) {
        if (stage == 0) {
            stage += 1
            return
        }
        x += v
        pc += 1
        stage = 0
    }
}

class Day10 : Solver {
    override fun solve (input: Sequence<String>) {
        val lines = input.toList()
        println(first(lines))
        println(second(lines))
    }
    
    fun first (input: List<String>): Int {
        return exec(input)
            .filter { it.cycles == 20 || (it.cycles + 20) % 40 == 0 }
            .map { it.cycles * it.x }
            .sum()
    }
    
    fun second (input: List<String>): String {
        var screen = mutableListOf<Char>()
        var x = 0
        for (cpu in exec(input)) {
            if ((cpu.cycles - 1) % 40 == 0) {
                screen.add('\n')
                x = 0
            }
            if (x == cpu.x - 1 || x == cpu.x || x == cpu.x + 1) screen.add('#')
            else screen.add('.')
            x += 1
        }
        return screen.joinToString("")
    }
    
    fun parse (line: String): Instruction {
        val parts = line.split(" ")
        return when (parts[0]) {
            "noop" -> Instruction(Opcode.NOOP, 0)
            "addx" -> Instruction(Opcode.ADDX, parts[1].toInt())
            else -> error("invalid instruction")
        }
    }

    fun exec (input: List<String>) = sequence {
        val rom = input.map(::parse).toList()
        val cpu = Cpu(rom)
        while (!cpu.halt) {
            yield(cpu)
            cpu.cycle()
        }
    }
}