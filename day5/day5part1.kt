import java.io.File

fun main(args: Array<String>) {

    // Read file
    val rawInstructions = File("input.txt").bufferedReader().readText()
    val instructions = rawInstructions.split(",").map { it.toInt() }.toIntArray()

    // Process
    val computer = Computer()
    val input = 1
    computer.process(instructions, input)
}


class Computer {

    private var parameter1mode = ParameterMode.Position
    private var parameter2mode = ParameterMode.Position
    private var parameter3mode = ParameterMode.Position
    private var curPos = 0
    private var curInstruction = 0
    private var opCode = 0
    private var processing = true
    private var instructions = IntArray(0)

    fun process(inst: IntArray, input: Int): Int {

        instructions = inst

        // Processing Loop
        while(processing) {

            curInstruction = instructions[curPos]
            opCode = calcOpCode(curInstruction)

            parameter1mode = calcParameterMode(curInstruction, 100)
            parameter2mode = calcParameterMode(curInstruction, 1000)
            parameter3mode = calcParameterMode(curInstruction, 10000)

            // Debugging
            //println("Pos: " + curPos + ", Inst: " + instructions[curPos] + ", OpCode: " + curInstruction)
            //println("PM1: " + parameter1mode + ", PM2: " + parameter2mode + ", PM3: " + parameter3mode)

            when(opCode) {
                1 -> opAdd(instructions)
                2 -> opMultiply(instructions)
                3 -> opInput(instructions, input)
                4 -> opOutput(instructions)
                99 -> processing = false

                else -> processing = false
            }

        }

        return instructions[0]
    }

    private fun calcParameterMode(curInstruction: Int, divisor: Int): ParameterMode {
        val i = (curInstruction / divisor) % 10
        return if(i == 1) ParameterMode.Immediate else ParameterMode.Position
    }

    private fun calcOpCode(curInstruction: Int): Int {
        return curInstruction % 10
    }

    private fun opAdd(instructions: IntArray) {

        var p1 = instructions[curPos + 1]
        var p2 = instructions[curPos + 2]
        val resultLocation = instructions[curPos + 3]

        if(parameter1mode == ParameterMode.Position) p1 = instructions[p1]
        if(parameter2mode == ParameterMode.Position) p2 = instructions[p2]

        val result = p1 + p2
        instructions[resultLocation] = result

        curPos += 4
    }

    private fun opMultiply(instructions: IntArray) {

        var p1 = instructions[curPos + 1]
        var p2 = instructions[curPos + 2]
        val resultLocation = instructions[curPos + 3]

        if(parameter1mode == ParameterMode.Position) p1 = instructions[p1]
        if(parameter2mode == ParameterMode.Position) p2 = instructions[p2]

        val result = p1 * p2
        instructions[resultLocation] = result

        curPos += 4
    }

    private fun opInput(instructions: IntArray, input: Int) {
        val resultLocation = instructions[curPos + 1]
        instructions[resultLocation] = input

        curPos += 2
    }

    private fun opOutput(instructions: IntArray) {
        val resultLocation = instructions[curPos + 1]
        println("Output: " + instructions[resultLocation])

        curPos += 2
    }

    enum class ParameterMode {
        Position,
        Immediate
    }
}