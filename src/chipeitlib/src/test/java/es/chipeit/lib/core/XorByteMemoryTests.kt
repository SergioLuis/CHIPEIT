package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class XorByteMemoryTests {
    companion object {
        internal fun switchTestFrom(memory: IMemory<Byte>, index: Int, with: Byte, to: Byte): Byte {
            val from: Byte = memory[index]

            assertNotEquals(from, to)

            memory[index] = with
            assertEquals(to, memory[index])

            memory[index] = with
            assertEquals(from, memory[index])

            return from
        }

        internal fun noSwitchTestFrom(memory: IMemory<Byte>, index: Int, with: Byte): Byte {
            val from: Byte = memory[index]

            memory[index] = with
            assertEquals(from, memory[index])

            return from
        }
    }

    @Test
    fun xorTest() {
        val ones: Byte = 0xFF.toByte()

        var memory = ByteMemory(ByteArray(6))
        var xorByteMemory = XorByteMemory(memory)

        for (index in 0..memory.size - 1)
            assertEquals(0x00, memory[index])

        // No switch from zeros with zeros
        assertEquals(
                0x00,
                noSwitchTestFrom(xorByteMemory, 0, 0x00)
        )

        // Switch from zeros with ones to ones
        assertEquals(
                0x00,
                switchTestFrom(xorByteMemory, 1, ones, ones)
        )

        // No switch from ones with zeros
        memory[2] = ones
        assertEquals(
                ones,
                noSwitchTestFrom(xorByteMemory, 2, 0x00)
        )

        // Switch from ones with ones to zeros
        memory[3] = ones
        assertEquals(
                ones,
                switchTestFrom(xorByteMemory, 3, ones, 0x00)
        )

        // Nibble switch
        memory[4] = 0x0F
        assertEquals(
                0x0F,
                switchTestFrom(xorByteMemory, 4, 0xFF.toByte(), 0xF0.toByte())
        )

        // Half nibble switch
        memory[5] = 0x3C
        assertEquals(
                0x3C,
                switchTestFrom(xorByteMemory, 5, 0xFF.toByte(), 0xC3.toByte())
        )
    }
}
