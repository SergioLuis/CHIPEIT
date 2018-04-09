package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

// 00E0 - CLS
internal fun cls(registers: IRegisters, graphicsMemory: IMemory<Byte>) {
    graphicsMemory.fill(0)
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
internal fun addVxByte(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF

    val vx = registers.v[x].toInt() and 0xFF
    val byte = instruction and 0x00FF

    registers.v[x] = (vx + byte).toByte()

    registers.pc += 2
}

// 8xy0 - LD Vx, Vy
internal fun ldVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    registers.v[x] = registers.v[y]

    registers.pc += 2
}

// 8xy1 - OR Vx, Vy
internal fun orVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    val vx = registers.v[x].toInt()
    val vy = registers.v[y].toInt()

    registers.v[x] = (vx or vy).toByte()

    registers.pc += 2
}

// 8xy2 - AND Vx, Vy
internal fun andVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    val vx = registers.v[x].toInt()
    val vy = registers.v[y].toInt()

    registers.v[x] = (vx and vy).toByte()

    registers.pc += 2
}

// 8xy3 - XOR Vx, Vy
internal fun xorVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    val vx = registers.v[x].toInt()
    val vy = registers.v[y].toInt()

    registers.v[x] = (vx xor vy).toByte()

    registers.pc += 2
}

// 8xy4 - ADD Vx, Vy

// 8xy5 - SUB Vx, Vy

// 8xy6 - SHR Vx {, Vy}
internal fun shrVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF

    registers.v[0xF] = if (x and 0x1 != 0) 1 else 0

    val vx = registers.v[x].toInt() and 0xFF

    registers.v[x] = (vx / 2).toByte()

    registers.pc += 2
}

// 8xy7 - SUBN Vx, Vy

// 8xyE - SHL Vx {, Vy}
internal fun shlVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF

    registers.v[0xF] = if (x and 0x8 != 0) 1 else 0

    val vx = registers.v[x].toInt() and 0xFF

    registers.v[x] = (vx * 2).toByte()

    registers.pc += 2
}

// 9xy0 - SNE Vx, Vy

// Annn - LD I, addr

// Bnnn - JP V0, addr

// Cxkk - RND Vx, byte

// Dxyn - RND Vx, byte

// Ex9E - SKP Vx

// ExA1 - SKNP Vx

// Fx07 - LD Vx, DT

// Fx0A - LD Vx, K

// Fx15 - LD DR, Vx

// Fx18 - LD ST, Vx

// Fx1E - ADD I, Vx

// Fx29 - LD F, Vx

// Fx33 - LD B, Vx

// Fx55 - LD [I], Vx

// Fx65 - LD Vx, [I]
