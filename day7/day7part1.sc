import scala.io.Source
import scala.collection.mutable.Queue
import scala.language.postfixOps

class Computer {

  var parameter1mode = ParameterMode.Position
  var parameter2mode = ParameterMode.Position
  var parameter3mode = ParameterMode.Position
  var curPos = 0
  var curInstruction = 0
  var opCode = 0
  var processing = true
  var instructions = new Array[Int](0)
  var inputQueue = new Queue[Int]()
  var output = 0

  def process(): Int = {

    // Processing Loop
    while(processing) {

      curInstruction = instructions(curPos)
      opCode = calcOpCode(curInstruction)

      parameter1mode = calcParameterMode(curInstruction, 100)
      parameter2mode = calcParameterMode(curInstruction, 1000)
      parameter3mode = calcParameterMode(curInstruction, 10000)

      // Debugging
      //println("Pos: " + curPos + ", Inst: " + instructions[curPos] + ", OpCode: " + curInstruction)
      //println("PM1: " + parameter1mode + ", PM2: " + parameter2mode + ", PM3: " + parameter3mode)

      opCode match {
        case 1 => opAdd(instructions)
        case 2 => opMultiply(instructions)
        case 3 => opInput(instructions)
        case 4 => opOutput(instructions)
        case 5 => opJumpIfTrue(instructions)
        case 6 => opJumpIfFalse(instructions)
        case 7 => opLessThan(instructions)
        case 8 => opEquals(instructions)
        case 9 => processing = false

        case _ => processing = false
      }

    }

    return output
  }

  def addInputToQueue(input: Int) {
    inputQueue.addOne(input)
  }

  def calcParameterMode(curInstruction: Int, divisor: Int): ParameterMode.Value = {
    val i = (curInstruction / divisor) % 10
    return if(i == 1) ParameterMode.Immediate else ParameterMode.Position
  }

  private def calcOpCode(curInstruction: Int): Int = {
    return curInstruction % 10
  }

  private def opAdd(instructions: Array[Int]) {

    var p1 = instructions(curPos + 1)
    var p2 = instructions(curPos + 2)
    val resultLocation = instructions(curPos + 3)

    if(parameter1mode == ParameterMode.Position) p1 = instructions(p1)
    if(parameter2mode == ParameterMode.Position) p2 = instructions(p2)

    val result = p1 + p2
    instructions(resultLocation) = result

    curPos += 4
  }

  private def opMultiply(instructions: Array[Int]) {

    var p1 = instructions(curPos + 1)
    var p2 = instructions(curPos + 2)
    val resultLocation = instructions(curPos + 3)

    if(parameter1mode == ParameterMode.Position) p1 = instructions(p1)
    if(parameter2mode == ParameterMode.Position) p2 = instructions(p2)

    val result = p1 * p2
    instructions(resultLocation) = result

    curPos += 4
  }

  private def opJumpIfTrue(instructions: Array[Int]) {

    var p1 = instructions(curPos + 1)
    var p2 = instructions(curPos + 2)

    if(parameter1mode == ParameterMode.Position) p1 = instructions(p1)
    if(parameter2mode == ParameterMode.Position) p2 = instructions(p2)

    if(p1 != 0) curPos = p2 else curPos += 3
  }

  private def opJumpIfFalse(instructions: Array[Int]) {

    var p1 = instructions(curPos + 1)
    var p2 = instructions(curPos + 2)

    if(parameter1mode == ParameterMode.Position) p1 = instructions(p1)
    if(parameter2mode == ParameterMode.Position) p2 = instructions(p2)

    if(p1 == 0) curPos = p2 else curPos += 3
  }

  private def opLessThan(instructions: Array[Int]) {

    var p1 = instructions(curPos + 1)
    var p2 = instructions(curPos + 2)
    val resultLocation = instructions(curPos + 3)

    if(parameter1mode == ParameterMode.Position) p1 = instructions(p1)
    if(parameter2mode == ParameterMode.Position) p2 = instructions(p2)

    if(p1 < p2) instructions(resultLocation) = 1 else instructions(resultLocation) = 0

    curPos += 4
  }

  private def opEquals(instructions: Array[Int]) {

    var p1 = instructions(curPos + 1)
    var p2 = instructions(curPos + 2)
    val resultLocation = instructions(curPos + 3)

    if(parameter1mode == ParameterMode.Position) p1 = instructions(p1)
    if(parameter2mode == ParameterMode.Position) p2 = instructions(p2)

    if(p1 == p2) instructions(resultLocation) = 1 else instructions(resultLocation) = 0

    curPos += 4
  }

  private def opInput(instructions: Array[Int]) {
    val resultLocation = instructions(curPos + 1)
    instructions(resultLocation) = inputQueue.dequeue()

    curPos += 2
  }

  private def opOutput(instructions: Array[Int]) {
    val resultLocation = instructions(curPos + 1)
    output =  instructions(resultLocation)

    curPos += 2
  }

  object ParameterMode extends Enumeration {
    val Position, Immediate = Value
  }
}

class AmplifierCircuit (instructions: Array[Int], phaseSettings: List[Int]) {

  val ampA = new Computer()
  val ampB = new Computer()
  val ampC = new Computer()
  val ampD = new Computer()
  val ampE = new Computer()

  ampA.addInputToQueue(phaseSettings(0))
  ampB.addInputToQueue(phaseSettings(1))
  ampC.addInputToQueue(phaseSettings(2))
  ampD.addInputToQueue(phaseSettings(3))
  ampE.addInputToQueue(phaseSettings(4))

  ampA.instructions = instructions.clone()
  ampB.instructions = instructions.clone()
  ampC.instructions = instructions.clone()
  ampD.instructions = instructions.clone()
  ampE.instructions = instructions.clone()

  def run(): Int = {

    var thrust = 0

    ampA.addInputToQueue(thrust)
    thrust = ampA.process()

    ampB.addInputToQueue(thrust)
    thrust = ampB.process()

    ampC.addInputToQueue(thrust)
    thrust = ampC.process()

    ampD.addInputToQueue(thrust)
    thrust = ampD.process()

    ampE.addInputToQueue(thrust)
    thrust = ampE.process()

    return thrust

  }
}

// Read file
val rawInstructions = Source.fromFile("input.txt").getLines.mkString
val instructions = rawInstructions.split(",").map(_.toInt)

// Calculate permutations of phasesettings
val phaseSettings = List(0, 1, 2, 3, 4).permutations

// Process
var results: Map[List[Int], Int] = Map()

for(permutation <- phaseSettings) {
  var amplifierCircuit = new AmplifierCircuit(instructions, permutation)
  val result = amplifierCircuit.run()

  results += (permutation -> result)
}

print("Result: " + results.valuesIterator.max)