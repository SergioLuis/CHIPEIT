package es.chipeit.lib.core

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

class InstructionSetTests {
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
}
