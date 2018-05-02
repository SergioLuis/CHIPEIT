package es.chipeit.lib.core

import es.chipeit.lib.interfaces.*

internal class Cpu(
        private val registers: IRegisters,
        private val delayTimer: ITimer,
        private val soundTimer: ITimer,
        private val stack: IMemory<Int>,
        private val memory: IMemory<Byte>,
        private val graphicMemory: ICoreGraphicMemory,
        private val keyboard: ICoreKeyboard
) : IClockObserver {
    init {
        registers.pc = Constants.CodeStartAddress
        registers.sp = -1

        if (stack.size < Constants.StackDepth)
            throw IllegalArgumentException(
                    "Parameter stack has ${stack.size} elements, " +
                            "at least 16 needed")
    }

    override fun onClockTick() {
        val higherBytes = memory[registers.pc].toInt() and 0xFF
        val lowerBytes = memory[registers.pc + 1].toInt() and 0xFF
        val instruction = higherBytes shl 2 * 4 or lowerBytes

        decodeAndExecute(instruction)
    }

    private fun decodeAndExecute(instruction: Int) {
        when (instruction and 0xF000) {
            // 0nnn - SYS addr
            // 00E0 - CLS
            // 00EE - RET
            0x0000 -> when (instruction) {
                0x00E0 -> cls(registers, graphicMemory)
                0x00EE -> ret(registers, stack)
                else -> sysAddr(registers)
            }

            // 1nnn - JP addr
            0x1000 -> jpAddr(instruction, registers)

            // 2nnn - CALL addr
            0x2000 -> call(instruction, registers, stack)

            // 3xkk - SE Vx, byte
            0x3000 -> seVxByte(instruction, registers)

            // 4xkk - SNE Vx, byte
            0x4000 -> sneVxByte(instruction, registers)

            // 5xy0 - SE Vx, Vy
            0x5000 -> when (instruction and 0xF00F) {
                0x5000 -> seVxVy(instruction, registers)
                else -> throw IllegalStateException() // FIXME: integration branch
            }

            // 6xkk - LD Vx, byte
            0x6000 -> ldVxByte(instruction, registers)

            // 7xkk - ADD Vx, byte
            0x7000 -> addVxByte(instruction, registers)

            // 8xy0 - LD Vx, Vy
            // 8xy1 - OR Vx, Vy
            // 8xy2 - AND Vx, Vy
            // 8xy3 - XOR Vx, Vy
            // 8xy4 - ADD Vx, Vy
            // 8xy5 - SUB Vx, Vy
            // 8xy6 - SHR Vx {, Vy}
            // 8xy7 - SUBN Vx, Vy
            // 8xyE - SHL Vx {, Vy}
            0x8000 -> when(instruction and 0xF00F) {
                0x8000 -> ldVxVy(instruction, registers)
                0x8001 -> orVxVy(instruction, registers)
                0x8002 -> andVxVy(instruction, registers)
                0x8003 -> xorVxVy(instruction, registers)
                0x8004 -> addVxVy(instruction, registers)
                0x8005 -> subVxVy(instruction, registers)
                0x8006 -> shrVxVy(instruction, registers)
                0x8007 -> subnVxVy(instruction, registers)
                0x800E -> shlVxVy(instruction, registers)
                else -> haltAndCatchFire(instruction)
            }

            // 9xy0 - SNE Vx, Vy
            0x9000 -> when(instruction and 0xF00F) {
                0x9000 -> sneVxVy(instruction, registers)
                else -> haltAndCatchFire(instruction)
            }
            0x9000 -> TODO("Instruction $instruction not implemented")

            // Annn - LD I, addr
            0xA000 -> ldIAddr(instruction, registers)

            // Bnnn - JP V0, addr
            0xB000 -> jpV0Addr(instruction, registers)

            // Cxkk - RND Vx, byte
            0xC000 -> rndVxByte(instruction, registers)

            // Dxyn - DRW Vx, Vy, nibble
            0xD000 -> TODO("Instruction $instruction not implemented")

            // Ex9E - SKP Vx
            // ExA1 - SKNP Vx
            0xE000 -> when (instruction and 0xF0FF) {
                0xE09E -> skpVx(instruction, registers, keyboard)
                0xE0A1 -> sknpVx(instruction, registers, keyboard)
                else -> haltAndCatchFire(instruction)
            }

            // Fx07 - LD Vx, DT
            // Fx0A - LD Vx, K
            // Fx15 - LD DR, Vx
            // Fx18 - LD ST, Vx
            // Fx1E - ADD I, Vx
            // Fx29 - LD F, Vx
            // Fx33 - LD B, Vx
            // Fx55 - LD [I], Vx
            // Fx65 - LD Vx, [I]
            0xF000 -> when(instruction and 0xF0FF) {
                0xF007 -> ldVxTimer(instruction, registers, delayTimer)
                0xF00A -> ldVxK(instruction, registers, keyboard)
                0xF015 -> ldTimerVx(instruction, registers, delayTimer)
                0xF018 -> ldTimerVx(instruction, registers, soundTimer)
                0xF01E -> addIVx(instruction, registers)
                0xF029 -> ldFVx(instruction, registers)
                0xF033 -> ldBVx(instruction, registers, memory)
                0xF055 -> ldIVx(instruction, registers, memory)
                0xF065 -> ldVxI(instruction, registers, memory)
                else -> haltAndCatchFire(instruction)
            }

            else -> haltAndCatchFire(instruction)
        }
    }

    private fun haltAndCatchFire(instruction: Int) {
        throw IllegalStateException(
                "The instruction $instruction does not comply with " +
                        "the original CHIP-8 specification")
    }
}
