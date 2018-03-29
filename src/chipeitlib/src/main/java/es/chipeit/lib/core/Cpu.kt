package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClockObserver
import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

internal class Cpu(
        private val memory: IMemory<Byte>,
        private val registers: IRegisters
) : IClockObserver {

    init {
        registers.pc = 0x200
    }

    override fun onClockTick() {
        var instruction: Int = 0
        instruction = instruction or (memory[registers.pc++].toInt() shl 4)
        instruction = instruction or memory[registers.pc].toInt()

        decodeAndExecute(instruction)
    }

    private fun decodeAndExecute(instruction: Int) {
        when (instruction and 0xF000) {
            // 0nnn - SYS addr
            // 00E0 - CLS
            // 00EE - RET
            0x0000 -> TODO()

            // 1nnn - JP addr
            0x1000 -> TODO()

            // 2nnn - CALL addr
            0x2000 -> TODO()

            // 3xkk - SE Vx, byte
            0x3000 -> TODO()

            // 4xkk - SNE Vx, byte
            0x4000 -> TODO()

            // 5xy0 - SE Vx, Vy
            0x5000 -> TODO()

            // 6xkk - LD Vx, byte
            0x6000 -> TODO()

            // 7xkk - ADD Vx, byte
            0x7000 -> TODO()

            // 8xy0 - LD Vx, Vy
            // 8xy1 - OR Vx, Vy
            // 8xy2 - AND Vx, Vy
            // 8xy3 - XOW Vx, Vy
            // 8xy4 - ADD Vx, Vy
            // 8xy5 - SUB Vx, Vy
            // 8xy6 - SHR Vx {, Vy}
            // 8xy7 - SUBN Vx, Vy
            // 8xyE - SHL Vx {, Vy}
            0x8000 -> TODO()

            // 9xy0 - SNE Vx, Vy
            0x9000 -> TODO()

            // Annn - LD I, addr
            0xA000 -> TODO()

            // Bnnn - JP V0, addr
            0xB000 -> TODO()

            // Cxkk - RND Vx, byte
            0xC000 -> TODO()

            // Dxyn - RND Vx, byte
            0xD000 -> TODO()

            // Ex9E - SKP Vx
            // ExA1 - SKNP Vx
            0xE000 -> TODO()

            // Fx07 - LD Vx, DT
            // Fx0A - LD Vx, K
            // Fx15 - LD DR, Vx
            // Fx18 - LD ST, Vx
            // Fx1E - ADD I, Vx
            // Fx29 - LD F, Vx
            // Fx33 - LD B, Vx
            // Fx55 - LD [I], Vx
            // Fx65 - LD Vx, [I]
            0xF000 -> TODO()
            else -> {
                throw IllegalStateException(
                        "The instruction $instruction does not comply with " +
                                "the original CHIP-8 specification")
            }
        }
    }
}
