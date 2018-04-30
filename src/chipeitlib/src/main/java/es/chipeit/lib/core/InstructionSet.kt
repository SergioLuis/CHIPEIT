package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ICoreGraphicMemory
import es.chipeit.lib.interfaces.ICoreKeyboard
import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters
import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.IUserKeyboard

// 0nnn - SYS addr
internal fun sysAddr(registers: IRegisters) {
    registers.pc += 2
}

// 00E0 - CLS
internal fun cls(registers: IRegisters, graphicMemory: ICoreGraphicMemory) {
    graphicMemory.clear()

    registers.pc += 2
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
    val x = instruction shr 2 * 4 and 0xF
    val byte = (instruction and 0x00FF).toByte()

    registers.v[x] = byte

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
internal fun addVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    val vx = registers.v[x].toInt() and 0xFF
    val vy = registers.v[y].toInt() and 0xFF

    val add = vx + vy

    registers.v[0xF] = (add shr 8).toByte()
    registers.v[x] = add.toByte()

    registers.pc += 2
}

// 8xy5 - SUB Vx, Vy
internal fun subVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    val vx = registers.v[x].toInt() and 0xFF
    val vy = registers.v[y].toInt() and 0xFF

    val add = vx - vy

    registers.v[x] = add.toByte()
    registers.v[0xF] = if (add > 0) 1 else 0

    registers.pc += 2
}

// 8xy6 - SHR Vx {, Vy}
internal fun shrVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF

    registers.v[0xF] = if (x and 0x1 != 0) 1 else 0

    val vx = registers.v[x].toInt() and 0xFF

    registers.v[x] = (vx / 2).toByte()

    registers.pc += 2
}

// 8xy7 - SUBN Vx, Vy
internal fun subnVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF

    val vx = registers.v[x].toInt() and 0xFF
    val vy = registers.v[y].toInt() and 0xFF

    val add = vy - vx

    registers.v[0xF] = if (add > 0) 1 else 0

    registers.v[x] = add.toByte()

    registers.pc += 2
}

// 8xyE - SHL Vx {, Vy}
internal fun shlVxVy(instruction: Int, registers: IRegisters) {
    val x = instruction shr 2 * 4 and 0xF

    registers.v[0xF] = if (x and 0x8 != 0) 1 else 0

    val vx = registers.v[x].toInt() and 0xFF

    registers.v[x] = (vx * 2).toByte()

    registers.pc += 2
}

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

// Dxyn - DRW Vx, Vy, Nibble
internal fun drwVxVyNibble(
        instruction: Int,
        registers: IRegisters,
        memory: IMemory<Byte>,
        graphicMemory: ICoreGraphicMemory
) {
    val x = instruction shr 2 * 4 and 0xF
    val y = instruction shr 1 * 4 and 0xF
    val height = instruction and 0xF

    val vx = registers.v[x].toInt() and 0xFF
    val vy = registers.v[y].toInt() and 0xFF

    var pixelCleared = false
    for (i in 0 until height) {
        val spriteRow = memory[registers.i + i]
        pixelCleared = pixelCleared || graphicMemory.drawLine(vx, vy + i, spriteRow)
    }
    registers.v[0xF] = if (pixelCleared) 1 else 0

    registers.pc += 2
}

// Ex9E - SKP Vx
internal fun skpVx(instruction: Int, registers: IRegisters, keyboard: ICoreKeyboard) {
    val x = instruction shr 2 * 4 and 0xF

    val vx = registers.v[x].toInt()

    val expectedKey = IUserKeyboard.Keys.values()[vx]

    if (keyboard.isPressed(expectedKey))
        registers.pc += 2

    registers.pc += 2
}

// ExA1 - SKNP Vx
internal fun sknpVx(instruction: Int, registers: IRegisters, keyboard: ICoreKeyboard) {
    val x = instruction shr 2 * 4 and 0xF

    val vx = registers.v[x].toInt()

    val expectedKey = IUserKeyboard.Keys.values()[vx]

    if (!keyboard.isPressed(expectedKey))
        registers.pc += 2

    registers.pc += 2
}

// Fx07 - LD Vx, DT
internal fun ldVxTimer(instruction: Int, registers: IRegisters, timer: ITimer) {
    val x = instruction shr 2 * 4 and 0xF

    registers.v[x] = timer.t

    registers.pc += 2
}

// Fx0A - LD Vx, K
internal fun ldVxK(instruction: Int, registers: IRegisters, keyboard: ICoreKeyboard) {
    /*
        Execution is paused avoiding the program counter to be updated until a key is released.
        That way, this instruction is executed over and over until a key is released.
        This behavior is the same as in original COSMAC VIP emulator:
        https://retrocomputing.stackexchange.com/questions/358/how-are-held-down-keys-handled-in-chip-8
    */
    if (keyboard.isCapturingNextKeyRelease) {
        keyboard.updateSoundTimerCounterIfNeeded()
        return
    }

    if (keyboard.capturedKeyRelease == IUserKeyboard.Keys.NONE) {
        keyboard.captureNextKeyRelease()
        return
    }

    if (keyboard.soundTimer.isActive())
        return

    val x = instruction shr 2 * 4 and 0xF

    registers.v[x] = keyboard.capturedKeyRelease.data.id

    keyboard.clearLastCapturedKeyRelease()

    registers.pc += 2
}

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

    val vx = registers.v[x].toInt() and 0xFF

    registers.i = Constants.HexFontStart + vx * Constants.CharacterSpriteLength

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
