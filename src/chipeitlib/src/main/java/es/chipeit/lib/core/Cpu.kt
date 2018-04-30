package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClockObserver
import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

internal class Cpu(
        private val memory: IMemory<Byte>,
        private val graphicsMemory: IMemory<Byte>,
        private val registers: IRegisters,
        private val stack: IMemory<Int>,
        private val keyboard: Keyboard
) : IClockObserver {

    init {
        registers.pc = 0x200
        registers.sp = -1

        if (stack.size < 16)
            throw IllegalArgumentException(
                    "Parameter stack has ${stack.size} elements, " +
                            "at least 16 needed")
    }

    override fun onClockTick() {
        val higherBytes = memory[registers.pc].toInt()
        val lowerBytes = memory[registers.pc + 1].toInt()
        val instruction = (higherBytes shl 8) or lowerBytes

        decodeAndExecute(instruction)
    }

    private fun decodeAndExecute(instruction: Int) {
        when (instruction and 0xF000) {
            // 0nnn - SYS addr
            // 00E0 - CLS
            // 00EE - RET
            0x0000 -> when (instruction) {
                0x00E0 -> cls(registers, graphicsMemory)
                0x00EE -> ret(registers, stack)
                else -> return // 0nnn - SYS addr (unused)
            }

            // 1nnn - JP addr
            0x1000 -> jpAddr(instruction, registers)

            // 2nnn - CALL addr
            0x2000 -> call(instruction, registers, stack)

            // 3xkk - SE Vx, byte
            0x3000 -> TODO("Instruction $instruction not implemented")

            // 4xkk - SNE Vx, byte
            0x4000 -> TODO("Instruction $instruction not implemented")

            // 5xy0 - SE Vx, Vy
            0x5000 -> TODO("Instruction $instruction not implemented")

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

            0x8000 -> when (instruction and 0xF00F) {
                0x8000 -> ldVxVy(instruction, registers)
                0x8001 -> orVxVy(instruction, registers)
                0x8002 -> andVxVy(instruction, registers)
                0x8003 -> xorVxVy(instruction, registers)
                0x8006 -> shrVxVy(instruction, registers)
                0x800E -> shlVxVy(instruction, registers)
                else -> {
                    throw IllegalStateException(
                            "The instruction $instruction does not comply with " +
                                    "the original CHIP-8 specification")
                }
            }

            // 9xy0 - SNE Vx, Vy
            0x9000 -> TODO("Instruction $instruction not implemented")

            // Annn - LD I, addr
            0xA000 -> TODO("Instruction $instruction not implemented")

            // Bnnn - JP V0, addr
            0xB000 -> TODO("Instruction $instruction not implemented")

            // Cxkk - RND Vx, byte
            0xC000 -> TODO("Instruction $instruction not implemented")

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
            0xF000 -> when (instruction and 0xF0FF) {
                0xF00A -> ldVxK(instruction, registers, keyboard)
                else -> TODO("Instruction $instruction not implemented")
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
