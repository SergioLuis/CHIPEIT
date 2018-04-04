package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.times

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters
import es.chipeit.lib.io.Keyboard

class InstructionSetTests {
    @Test
    fun clsTest() {
        val graphicMemory = Mockito.mock(IMemory::class.java)

        cls(graphicMemory as IMemory<Byte>)

        Mockito.verify(
                 graphicMemory,
                 times(1)
        ).fill(0)
    }

    @Test
    fun retTest() {
        val registers = Registers(ByteMemory(ByteArray(16)))
        val stackMock = Mockito.mock(IMemory::class.java) as IMemory<Int>

        Mockito.`when`(stackMock[2]).thenReturn(0x0222)
        Mockito.`when`(stackMock[1]).thenReturn(0x0456)
        Mockito.`when`(stackMock[0]).thenReturn(0x0789)

        registers.sp = 2

        ret(registers, stackMock)

        assertEquals(1, registers.sp)
        assertEquals(0x222, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[2]

        ret(registers, stackMock)

        assertEquals(0, registers.sp)
        assertEquals(0x456, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[1]

        ret(registers, stackMock)

        assertEquals(-1, registers.sp)
        assertEquals(0x789, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[0]
    }

    @Test
    fun jpAddrTest() {
        val registersMock = Mockito.mock(IRegisters::class.java)

        jpAddr(0x1225, registersMock)
        jpAddr(0x1512, registersMock)
        jpAddr(0x1FFF, registersMock)

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0225

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0512

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0FFF
    }

    @Test
    fun callTest() {
        val registers = Registers(ByteMemory(ByteArray(16)))
        val stackMock = Mockito.mock(IMemory::class.java) as IMemory<Int>

        registers.sp = -1
        registers.pc = 0x789

        call(0x2345, registers, stackMock)

        assertEquals(0, registers.sp)
        assertEquals(0x345, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[0] = 0x789

        call(0x2200, registers, stackMock)

        assertEquals(1, registers.sp)
        assertEquals(0x200, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[1] = 0x345

        call(0x2FFF, registers, stackMock)

        assertEquals(2, registers.sp)
        assertEquals(0xFFF, registers.pc)
        Mockito.verify(
                stackMock,
                times(1)
        )[2] = 0x200
    }

    @Test
    fun ldVxByteTest() {
        val registersMemoryMock = Mockito.mock(IMemory::class.java)
        Mockito.`when`(registersMemoryMock.size).thenAnswer { 16 }

        val registersMock = Registers(registersMemoryMock as IMemory<Byte>)

        ldVxByte(0x6011, registersMock)
        ldVxByte(0x6122, registersMock)
        ldVxByte(0x6233, registersMock)
        ldVxByte(0x6344, registersMock)
        ldVxByte(0x6455, registersMock)
        ldVxByte(0x6566, registersMock)
        ldVxByte(0x6677, registersMock)
        ldVxByte(0x6788, registersMock)
        ldVxByte(0x6899, registersMock)
        ldVxByte(0x69AA, registersMock)
        ldVxByte(0x6ABB, registersMock)
        ldVxByte(0x6BCC, registersMock)
        ldVxByte(0x6CDD, registersMock)
        ldVxByte(0x6DEE, registersMock)
        ldVxByte(0x6EFF, registersMock)
        ldVxByte(0x6F00, registersMock)

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0] = 0x11

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[1] = 0x22

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[2] = 0x33

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[3] = 0x44

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[4] = 0x55

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[5] = 0x66

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[6] = 0x77

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[7] = 0x88.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[8] = 0x99.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[9] = 0xAA.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[10] = 0xBB.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[11] = 0xCC.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[12] = 0xDD.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[13] = 0xEE.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[14] = 0xFF.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[15] = 0x00
    }

    @Test
    fun skpVxTest() {
        val vRegsMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vRegsMock.size).thenReturn(16)
        Mockito.`when`(vRegsMock[0xA]).thenReturn(3)
        Mockito.`when`(vRegsMock[0xB]).thenReturn(8)

        val keyboard = Keyboard()
        keyboard.keyDown(Keyboard.Keys.KEY_3)
        keyboard.keyUp(Keyboard.Keys.KEY_8)

        val registersMock = Registers(vRegsMock)
        registersMock.pc = 0x0200

        skpVx(0xEA9E, registersMock, keyboard)

        assertEquals(0x0200 + 2, registersMock.pc)
        Mockito.verify(
                vRegsMock,
                times(1)
        )[0xA]

        skpVx(0xEB9E, registersMock, keyboard)

        assertEquals(0x0200 + 2, registersMock.pc)
        Mockito.verify(
                vRegsMock,
                times(1)
        )[0xB]
    }

    @Test
    fun sknpVxTest() {
        val vRegsMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vRegsMock.size).thenReturn(16)
        Mockito.`when`(vRegsMock[0xA]).thenReturn(3)
        Mockito.`when`(vRegsMock[0xB]).thenReturn(8)

        val keyboard = Keyboard()
        keyboard.keyUp(Keyboard.Keys.KEY_3)
        keyboard.keyDown(Keyboard.Keys.KEY_8)

        val registersMock = Registers(vRegsMock)
        registersMock.pc = 0x0200

        skpVx(0xEA9E, registersMock, keyboard)

        assertEquals(0x0200, registersMock.pc)
        Mockito.verify(
                vRegsMock,
                times(1)
        )[0xA]

        skpVx(0xEB9E, registersMock, keyboard)

        assertEquals(0x0200 + 2, registersMock.pc)
        Mockito.verify(
                vRegsMock,
                times(1)
        )[0xB]
    }
}
