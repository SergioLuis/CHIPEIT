package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IGraphicMemory
import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters
import es.chipeit.lib.interfaces.ITimer

// 00E0 - CLS
internal fun cls(registers: IRegisters, graphicMemory: IGraphicMemory) {
    graphicMemory.fill(0)
    registers.pc += 2
}

// 00EE - RET
internal fun ret(registers: IRegisters) {
    TODO()
}

// 1nnn - JP addr
internal fun jpAddr(instruction: Int, registers: IRegisters) {
    registers.pc = instruction and 0x0FFF
}

// 2nnn - CALL addr

// 3xkk - SE Vx, byte

// 4xkk - SNE Vx, byte

// 5xy0 - SE Vx, Vy

// 6xkk - LD Vx, byte
internal fun ldVxByte(instruction: Int, registers: IRegisters) {
    val register = (instruction and 0x0F00) shr 8
    val byte = (instruction and 0x00FF).toByte()
    registers.v[register] = byte
    registers.pc += 2
}

// 7xkk - ADD Vx, byte

// 8xy0 - LD Vx, Vy

// 8xy1 - OR Vx, Vy

// 8xy2 - AND Vx, Vy

// 8xy3 - XOW Vx, Vy

// 8xy4 - ADD Vx, Vy
internal fun addVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    var vx = registers.v[x].toInt() and 0xFF
    var vy = registers.v[y].toInt() and 0xFF

    val add = vx + vy

    registers.v[0xF] = (add shr 8).toByte()
    registers.v[x] = add.toByte()

    registers.pc += 2
}

// 8xy5 - SUB Vx, Vy
internal fun subVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    var vx = registers.v[x].toInt() and 0xFF
    var vy = registers.v[y].toInt() and 0xFF

    val add = vx - vy

    registers.v[x] = add.toByte()
    registers.v[0xF] = if (add > 0) 1 else 0

    registers.pc += 2
}

// 8xy6 - SHR Vx {, Vy}

// 8xy7 - SUBN Vx, Vy
internal fun subnVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    var vx = registers.v[x].toInt() and 0xFF
    var vy = registers.v[y].toInt() and 0xFF

    val add = vy - vx

    registers.v[0xF] = if (add > 0) 1 else 0

    registers.v[x] = add.toByte()

    registers.pc += 2
}

// 8xyE - SHL Vx {, Vy}

// 9xy0 - SNE Vx, Vy
internal fun sneVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    if (registers.v[x] != registers.v[y])
        registers.pc += 2

    registers.pc += 2
}

// Annn - LD I, addr

// Bnnn - JP V0, addr

// Cxkk - RND Vx, byte

// Dxyn - RND Vx, byte

// Ex9E - SKP Vx

// ExA1 - SKNP Vx

// Fx07 - LD Vx, DT
internal fun ldVxTimer(instruction: Int, registers: IRegisters, timer: ITimer) {
    val x = instruction shr 2 * 4 and 0xF

    registers.v[x] = timer.t

    registers.pc += 2
}

// Fx0A - LD Vx, K

// Fx15 - LD DT, Vx
// Fx18 - LD ST, Vx
internal fun ldTimerVx(instruction: Int, registers: IRegisters, timer: ITimer) {
    val x = instruction shr 2 * 4 and 0xF

    timer.t = registers.v[x]

    registers.pc += 2
}

// Fx1E - ADD I, Vx

// Fx29 - LD F, Vx
internal fun ldFVx(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF

    var vx = registers.v[x].toInt() and 0xFF

    if (vx > 0xF)
        throw IllegalStateException(
                "V%X must be in the range 0h-Fh but the variable value is %Xh".format(x, vx)
        )

    /*
        The hex font table starts at 0x000.
        Every character sprite has 5 bytes.
    */
    registers.i = vx * 5

    registers.pc += 2
}

// Fx33 - LD B, Vx
internal fun ldBVx(instruction: Int, registers: IRegisters, memory: IMemory<Byte>) {
    val x = instruction shr 2 * 4 and 0xF

    var vx = registers.v[x].toInt() and 0xFF

    var address = registers.i + 2
    var mod: Int
    var a = 1

    for (i in 1..3) {
        mod = vx % (10 * a)
        vx -= mod
        memory[address--] = (mod / a).toByte()
        a *= 10
    }

    registers.pc += 2
}

// Fx55 - LD [I], Vx

// Fx65 - LD Vx, [I]
