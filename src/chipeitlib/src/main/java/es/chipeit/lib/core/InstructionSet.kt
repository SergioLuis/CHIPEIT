package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

// 00E0 - CLS
internal fun cls(graphicsMemory: IMemory<Byte>) {
    graphicsMemory.fill(0)
}

// 00EE - RET
internal fun ret(registers: IRegisters, stack: IMemory<Int>) {
    registers.pc = stack[registers.sp]
    registers.sp -= 1
}

// 1nnn - JP addr
internal fun jpAddr(instruction: Int, registers: IRegisters) {
    registers.pc = instruction and 0x0FFF
}

// 2nnn - CALL addr
internal fun call(instruction: Int, registers: IRegisters, stack: IMemory<Int>) {
    registers.sp += 1
    stack[registers.sp] = registers.pc
    registers.pc = instruction and 0x0FFF
}

// 3xkk - SE Vx, byte

// 4xkk - SNE Vx, byte

// 5xy0 - SE Vx, Vy

// 6xkk - LD Vx, byte
internal fun ldVxByte(instruction: Int, registers: IRegisters) {
    val register = (instruction and 0x0F00) shr 8
    val byte = (instruction and 0x00FF).toByte()
    registers.v[register] = byte
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
