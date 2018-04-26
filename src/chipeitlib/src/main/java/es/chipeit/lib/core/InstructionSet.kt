package es.chipeit.lib.core

import java.lang.Math.random

import es.chipeit.lib.interfaces.IGraphicMemory
import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

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
internal fun seVxByte(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val byte = (instruction and 0xFF).toByte()

    if (registers.v[x] == byte)
        registers.pc += 2

    registers.pc += 2
}

// 4xkk - SNE Vx, byte
internal fun sneVxByte(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val byte = (instruction and 0xFF).toByte()

    if (registers.v[x] != byte)
        registers.pc += 2

    registers.pc += 2
}

// 5xy0 - SE Vx, Vy
internal fun seVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    if (registers.v[x] == registers.v[y])
        registers.pc += 2

    registers.pc += 2
}

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

// 8xy5 - SUB Vx, Vy

// 8xy6 - SHR Vx {, Vy}

// 8xy7 - SUBN Vx, Vy

// 8xyE - SHL Vx {, Vy}

// 9xy0 - SNE Vx, Vy

// Annn - LD I, addr
internal fun ldIAddr(instruction: Int, registers: IRegisters) {
    val addr = instruction and 0xFFF

    registers.i = addr.toShort()

    registers.pc += 2
}

// Bnnn - JP V0, addr
internal fun jpV0Addr(instruction: Int, registers: IRegisters) {
    val addr = instruction and 0xFFF

    val v0 = registers.v[0x0].toInt() and 0xFF

    registers.pc = addr + v0
}

// Cxkk - RND Vx, byte
internal fun rndVxByte(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val kk = (random() * 255).toInt()

    registers.v[x] = (x and kk).toByte()

    registers.pc += 2
}

// Dxyn - RND Vx, byte

// Ex9E - SKP Vx

// ExA1 - SKNP Vx

// Fx07 - LD Vx, DT

// Fx0A - LD Vx, K

// Fx15 - LD DR, Vx

// Fx18 - LD ST, Vx

// Fx1E - ADD I, Vx
internal fun addIVx(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF

    val vx = registers.v[x].toInt() and 0xFF

    registers.i = (registers.i + vx).toShort()

    registers.pc += 2
}

// Fx29 - LD F, Vx

// Fx33 - LD B, Vx

// Fx55 - LD [I], Vx
internal fun ldIVx(instruction: Int, registers: IRegisters, memory: IMemory<Byte>) {
    val x = instruction shr 2 * 4 and 0xF

    var address = registers.i.toInt()

    for (i in 0..x)
        memory[address++] = registers.v[i]

    registers.pc += 2
}

// Fx65 - LD Vx, [I]
internal fun ldVxI(instruction: Int, registers: IRegisters, memory: IMemory<Byte>) {
    val x = instruction shr 2 * 4 and 0xF

    var address = registers.i.toInt()

    for (i in 0..x)
        registers.v[i] = memory[address++]

    registers.pc +=2
}
