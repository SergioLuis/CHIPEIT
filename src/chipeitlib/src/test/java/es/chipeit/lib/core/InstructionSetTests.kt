package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import org.mockito.Mockito
import org.mockito.Mockito.times

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

class InstructionSetTests {
    @Test
    fun clsTest() {
        val graphicMemory = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.pc = 0x0200

        cls(registers, graphicMemory)

        assertEquals(0x0200 + 2, registers.pc)
        Mockito.verify(
                 graphicMemory,
                 times(1)
        ).fill(0)
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
    fun ldVxByteTest() {
        val registersMemoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(registersMemoryMock.size).thenAnswer { 16 }

        val registersMock = Registers(registersMemoryMock)
        registersMock.pc = 0x200

        ldVxByte(0x6011, registersMock)
        assertEquals(0x0200 + 2, registersMock.pc)

        ldVxByte(0x6122, registersMock)
        assertEquals(0x0200 + 4, registersMock.pc)

        ldVxByte(0x6233, registersMock)
        assertEquals(0x0200 + 6, registersMock.pc)

        ldVxByte(0x6344, registersMock)
        assertEquals(0x0200 + 8, registersMock.pc)

        ldVxByte(0x6455, registersMock)
        assertEquals(0x0200 + 10, registersMock.pc)

        ldVxByte(0x6566, registersMock)
        assertEquals(0x0200 + 12, registersMock.pc)

        ldVxByte(0x6677, registersMock)
        assertEquals(0x0200 + 14, registersMock.pc)

        ldVxByte(0x6788, registersMock)
        assertEquals(0x0200 + 16, registersMock.pc)

        ldVxByte(0x6899, registersMock)
        assertEquals(0x0200 + 18, registersMock.pc)

        ldVxByte(0x69AA, registersMock)
        assertEquals(0x0200 + 20, registersMock.pc)

        ldVxByte(0x6ABB, registersMock)
        assertEquals(0x0200 + 22, registersMock.pc)

        ldVxByte(0x6BCC, registersMock)
        assertEquals(0x0200 + 24, registersMock.pc)

        ldVxByte(0x6CDD, registersMock)
        assertEquals(0x0200 + 26, registersMock.pc)

        ldVxByte(0x6DEE, registersMock)
        assertEquals(0x0200 + 28, registersMock.pc)

        ldVxByte(0x6EFF, registersMock)
        assertEquals(0x0200 + 30, registersMock.pc)

        ldVxByte(0x6F00, registersMock)
        assertEquals(0x0200 + 32, registersMock.pc)

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
    fun addVxByteTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenReturn (16)
        Mockito.`when`(vMock[0x0]).thenReturn (0)
        Mockito.`when`(vMock[0x1]).thenReturn (8)
        Mockito.`when`(vMock[0x2]).thenReturn (16)
        Mockito.`when`(vMock[0x3]).thenReturn (24)
        Mockito.`when`(vMock[0x4]).thenReturn (32)
        Mockito.`when`(vMock[0x5]).thenReturn (40)
        Mockito.`when`(vMock[0x6]).thenReturn (48)
        Mockito.`when`(vMock[0x7]).thenReturn (56)
        Mockito.`when`(vMock[0x8]).thenReturn (72)
        Mockito.`when`(vMock[0x9]).thenReturn (88)
        Mockito.`when`(vMock[0xA]).thenReturn (104)
        Mockito.`when`(vMock[0xB]).thenReturn (-1)
        Mockito.`when`(vMock[0xC]).thenReturn (255.toByte())
        Mockito.`when`(vMock[0xD]).thenReturn (10)
        Mockito.`when`(vMock[0xE]).thenReturn (5)
        Mockito.`when`(vMock[0xF]).thenReturn (3)

        val registers = Registers(vMock)
        registers.pc = 0x200

        addVxByte(0x7011, registers)
        assertEquals(0x0200 + 2, registers.pc)

        addVxByte(0x7122, registers)
        assertEquals(0x0200 + 4, registers.pc)

        addVxByte(0x7233, registers)
        assertEquals(0x0200 + 6, registers.pc)

        addVxByte(0x7344, registers)
        assertEquals(0x0200 + 8, registers.pc)

        addVxByte(0x7455, registers)
        assertEquals(0x0200 + 10, registers.pc)

        addVxByte(0x7566, registers)
        assertEquals(0x0200 + 12, registers.pc)

        addVxByte(0x7677, registers)
        assertEquals(0x0200 + 14, registers.pc)

        addVxByte(0x7788, registers)
        assertEquals(0x0200 + 16, registers.pc)

        addVxByte(0x7899, registers)
        assertEquals(0x0200 + 18, registers.pc)

        addVxByte(0x79AA, registers)
        assertEquals(0x0200 + 20, registers.pc)

        addVxByte(0x7ABB, registers)
        assertEquals(0x0200 + 22, registers.pc)

        addVxByte(0x7BCC, registers)
        assertEquals(0x0200 + 24, registers.pc)

        addVxByte(0x7CDD, registers)
        assertEquals(0x0200 + 26, registers.pc)

        addVxByte(0x7DEE, registers)
        assertEquals(0x0200 + 28, registers.pc)

        addVxByte(0x7EFF, registers)
        assertEquals(0x0200 + 30, registers.pc)

        addVxByte(0x7F00, registers)
        assertEquals(0x0200 + 32, registers.pc)

        Mockito.verify(
                vMock,
        times(1)
        )[0x0] = 17

        Mockito.verify(
                vMock,
        times(1)
        )[0x1] = 42

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 67

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = 92

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 117

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = 142.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 167.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 192.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 225.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 258.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 291.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xB] = 203.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xC] = 476.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xD] = 248.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xE] = 260.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 3
    }

    @Test
    fun ldVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenAnswer { 16 }

        val registers = Registers(vMock)
        registers.pc = 0x200

        ldVxVy(0x8010, registers)
        assertEquals(0x0200 + 2, registers.pc)

        ldVxVy(0x8120, registers)
        assertEquals(0x0200 + 4, registers.pc)

        ldVxVy(0x8230, registers)
        assertEquals(0x0200 + 6, registers.pc)

        ldVxVy(0x8340, registers)
        assertEquals(0x0200 + 8, registers.pc)

        ldVxVy(0x8450, registers)
        assertEquals(0x0200 + 10, registers.pc)

        ldVxVy(0x8560, registers)
        assertEquals(0x0200 + 12, registers.pc)

        ldVxVy(0x8670, registers)
        assertEquals(0x0200 + 14, registers.pc)

        ldVxVy(0x8780, registers)
        assertEquals(0x0200 + 16, registers.pc)

        ldVxVy(0x8890, registers)
        assertEquals(0x0200 + 18, registers.pc)

        ldVxVy(0x89A0, registers)
        assertEquals(0x0200 + 20, registers.pc)

        ldVxVy(0x8AB0, registers)
        assertEquals(0x0200 + 22, registers.pc)

        ldVxVy(0x8BC0, registers)
        assertEquals(0x0200 + 24, registers.pc)

        ldVxVy(0x8CD0, registers)
        assertEquals(0x0200 + 26, registers.pc)

        ldVxVy(0x8DE0, registers)
        assertEquals(0x0200 + 28, registers.pc)

        ldVxVy(0x8EF0, registers)
        assertEquals(0x0200 + 30, registers.pc)

        ldVxVy(0x8F00, registers)
        assertEquals(0x0200 + 32, registers.pc)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = vMock[1]

        Mockito.verify(
                vMock,
                times(1)
        )[0x1] = vMock[2]

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = vMock[3]

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = vMock[4]

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = vMock[5]

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = vMock[6]

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = vMock[7]

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = vMock[8]

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = vMock[9]

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = vMock[11]

        Mockito.verify(
                vMock,
                times(1)
        )[0xB] = vMock[12]

        Mockito.verify(
                vMock,
                times(1)
        )[0xC] = vMock[13]

        Mockito.verify(
                vMock,
                times(1)
        )[0xD] = vMock[14]

        Mockito.verify(
                vMock,
                times(1)
        )[0xE] = vMock[15]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = vMock[0]
    }

    @Test
    fun orVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenAnswer { 16 }

        Mockito.`when`(vMock.size).thenReturn (16)
        Mockito.`when`(vMock[0x0]).thenReturn (0)
        Mockito.`when`(vMock[0x1]).thenReturn (8)
        Mockito.`when`(vMock[0x2]).thenReturn (16)
        Mockito.`when`(vMock[0x3]).thenReturn (24)
        Mockito.`when`(vMock[0x4]).thenReturn (32)
        Mockito.`when`(vMock[0x5]).thenReturn (40)
        Mockito.`when`(vMock[0x6]).thenReturn (48)
        Mockito.`when`(vMock[0x7]).thenReturn (56)
        Mockito.`when`(vMock[0x8]).thenReturn (72)
        Mockito.`when`(vMock[0x9]).thenReturn (88)
        Mockito.`when`(vMock[0xA]).thenReturn (100)
        Mockito.`when`(vMock[0xB]).thenReturn (104)
        Mockito.`when`(vMock[0xC]).thenReturn (108)
        Mockito.`when`(vMock[0xD]).thenReturn (128.toByte())
        Mockito.`when`(vMock[0xE]).thenReturn (130.toByte())
        Mockito.`when`(vMock[0xF]).thenReturn (150.toByte())

        val registers = Registers(vMock)
        registers.pc = 0x200

        orVxVy(0x8011, registers)
        assertEquals(0x0200 + 2, registers.pc)

        orVxVy(0x8121, registers)
        assertEquals(0x0200 + 4, registers.pc)

        orVxVy(0x8231, registers)
        assertEquals(0x0200 + 6, registers.pc)

        orVxVy(0x8341, registers)
        assertEquals(0x0200 + 8, registers.pc)

        orVxVy(0x8451, registers)
        assertEquals(0x0200 + 10, registers.pc)

        orVxVy(0x8561, registers)
        assertEquals(0x0200 + 12, registers.pc)

        orVxVy(0x8671, registers)
        assertEquals(0x0200 + 14, registers.pc)

        orVxVy(0x8781, registers)
        assertEquals(0x0200 + 16, registers.pc)

        orVxVy(0x8891, registers)
        assertEquals(0x0200 + 18, registers.pc)

        orVxVy(0x89A1, registers)
        assertEquals(0x0200 + 20, registers.pc)

        orVxVy(0x8AB1, registers)
        assertEquals(0x0200 + 22, registers.pc)

        orVxVy(0x8BC1, registers)
        assertEquals(0x0200 + 24, registers.pc)

        orVxVy(0x8CD1, registers)
        assertEquals(0x0200 + 26, registers.pc)

        orVxVy(0x8DE1, registers)
        assertEquals(0x0200 + 28, registers.pc)

        orVxVy(0x8EF1, registers)
        assertEquals(0x0200 + 30, registers.pc)

        orVxVy(0x8F01, registers)
        assertEquals(0x0200 + 32, registers.pc)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 8

        Mockito.verify(
                vMock,
                times(1)
        )[0x1] = 24

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 24

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = 56

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 40

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = 56

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 56

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 120

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 88

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 124

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 108

        Mockito.verify(
                vMock,
                times(1)
        )[0xB] = 108

        Mockito.verify(
                vMock,
                times(1)
        )[0xC] = 236.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xD] = 130.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xE] = 150.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 150.toByte()
    }

    @Test
    fun andVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenAnswer { 16 }

        Mockito.`when`(vMock.size).thenReturn (16)
        Mockito.`when`(vMock[0x0]).thenReturn (0)
        Mockito.`when`(vMock[0x1]).thenReturn (8)
        Mockito.`when`(vMock[0x2]).thenReturn (16)
        Mockito.`when`(vMock[0x3]).thenReturn (24)
        Mockito.`when`(vMock[0x4]).thenReturn (32)
        Mockito.`when`(vMock[0x5]).thenReturn (40)
        Mockito.`when`(vMock[0x6]).thenReturn (48)
        Mockito.`when`(vMock[0x7]).thenReturn (56)
        Mockito.`when`(vMock[0x8]).thenReturn (72)
        Mockito.`when`(vMock[0x9]).thenReturn (88)
        Mockito.`when`(vMock[0xA]).thenReturn (100)
        Mockito.`when`(vMock[0xB]).thenReturn (104)
        Mockito.`when`(vMock[0xC]).thenReturn (108)
        Mockito.`when`(vMock[0xD]).thenReturn (128.toByte())
        Mockito.`when`(vMock[0xE]).thenReturn (130.toByte())
        Mockito.`when`(vMock[0xF]).thenReturn (150.toByte())

        val registers = Registers(vMock)
        registers.pc = 0x200

        andVxVy(0x8012, registers)
        assertEquals(0x0200 + 2, registers.pc)

        andVxVy(0x8122, registers)
        assertEquals(0x0200 + 4, registers.pc)

        andVxVy(0x8232, registers)
        assertEquals(0x0200 + 6, registers.pc)

        andVxVy(0x8342, registers)
        assertEquals(0x0200 + 8, registers.pc)

        andVxVy(0x8452, registers)
        assertEquals(0x0200 + 10, registers.pc)

        andVxVy(0x8562, registers)
        assertEquals(0x0200 + 12, registers.pc)

        andVxVy(0x8672, registers)
        assertEquals(0x0200 + 14, registers.pc)

        andVxVy(0x8782, registers)
        assertEquals(0x0200 + 16, registers.pc)

        andVxVy(0x8892, registers)
        assertEquals(0x0200 + 18, registers.pc)

        andVxVy(0x89A2, registers)
        assertEquals(0x0200 + 20, registers.pc)

        andVxVy(0x8AB2, registers)
        assertEquals(0x0200 + 22, registers.pc)

        andVxVy(0x8BC2, registers)
        assertEquals(0x0200 + 24, registers.pc)

        andVxVy(0x8CD2, registers)
        assertEquals(0x0200 + 26, registers.pc)

        andVxVy(0x8DE2, registers)
        assertEquals(0x0200 + 28, registers.pc)

        andVxVy(0x8EF2, registers)
        assertEquals(0x0200 + 30, registers.pc)

        andVxVy(0x8F02, registers)
        assertEquals(0x0200 + 32, registers.pc)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x1] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 32

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = 32

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 48

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 8

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 72

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 64

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 96

        Mockito.verify(
                vMock,
                times(1)
        )[0xB] = 104

        Mockito.verify(
                vMock,
                times(1)
        )[0xC] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0xD] = 128.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xE] = 130.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 0
    }

    @Test
    fun xorVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenAnswer { 16 }

        Mockito.`when`(vMock.size).thenReturn (16)
        Mockito.`when`(vMock[0x0]).thenReturn (0)
        Mockito.`when`(vMock[0x1]).thenReturn (8)
        Mockito.`when`(vMock[0x2]).thenReturn (16)
        Mockito.`when`(vMock[0x3]).thenReturn (24)
        Mockito.`when`(vMock[0x4]).thenReturn (32)
        Mockito.`when`(vMock[0x5]).thenReturn (40)
        Mockito.`when`(vMock[0x6]).thenReturn (48)
        Mockito.`when`(vMock[0x7]).thenReturn (56)
        Mockito.`when`(vMock[0x8]).thenReturn (72)
        Mockito.`when`(vMock[0x9]).thenReturn (88)
        Mockito.`when`(vMock[0xA]).thenReturn (100)
        Mockito.`when`(vMock[0xB]).thenReturn (104)
        Mockito.`when`(vMock[0xC]).thenReturn (108)
        Mockito.`when`(vMock[0xD]).thenReturn (128.toByte())
        Mockito.`when`(vMock[0xE]).thenReturn (130.toByte())
        Mockito.`when`(vMock[0xF]).thenReturn (150.toByte())

        val registers = Registers(vMock)
        registers.pc = 0x200

        xorVxVy(0x8013, registers)
        assertEquals(0x0200 + 2, registers.pc)

        xorVxVy(0x8123, registers)
        assertEquals(0x0200 + 4, registers.pc)

        xorVxVy(0x8233, registers)
        assertEquals(0x0200 + 6, registers.pc)

        xorVxVy(0x8343, registers)
        assertEquals(0x0200 + 8, registers.pc)

        xorVxVy(0x8453, registers)
        assertEquals(0x0200 + 10, registers.pc)

        xorVxVy(0x8563, registers)
        assertEquals(0x0200 + 12, registers.pc)

        xorVxVy(0x8673, registers)
        assertEquals(0x0200 + 14, registers.pc)

        xorVxVy(0x8783, registers)
        assertEquals(0x0200 + 16, registers.pc)

        xorVxVy(0x8893, registers)
        assertEquals(0x0200 + 18, registers.pc)

        xorVxVy(0x89A3, registers)
        assertEquals(0x0200 + 20, registers.pc)

        xorVxVy(0x8AB3, registers)
        assertEquals(0x0200 + 22, registers.pc)

        xorVxVy(0x8BC3, registers)
        assertEquals(0x0200 + 24, registers.pc)

        xorVxVy(0x8CD3, registers)
        assertEquals(0x0200 + 26, registers.pc)

        xorVxVy(0x8DE3, registers)
        assertEquals(0x0200 + 28, registers.pc)

        xorVxVy(0x8EF3, registers)
        assertEquals(0x0200 + 30, registers.pc)

        xorVxVy(0x8F03, registers)
        assertEquals(0x0200 + 32, registers.pc)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 8

        Mockito.verify(
                vMock,
                times(1)
        )[0x1] = 24

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 8

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = 56

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 8

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = 24

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 8

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 112

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 60

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 12

        Mockito.verify(
                vMock,
                times(1)
        )[0xB] = 4

        Mockito.verify(
                vMock,
                times(1)
        )[0xC] = 236.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xD] = 2

        Mockito.verify(
                vMock,
                times(1)
        )[0xE] = 20

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 150.toByte()
    }

    @Test
    fun shrVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenAnswer { 16 }

        Mockito.`when`(vMock.size).thenReturn (16)
        Mockito.`when`(vMock[0x0]).thenReturn (0)
        Mockito.`when`(vMock[0x1]).thenReturn (8)
        Mockito.`when`(vMock[0x2]).thenReturn (7)
        Mockito.`when`(vMock[0x3]).thenReturn (24)
        Mockito.`when`(vMock[0x4]).thenReturn (32)
        Mockito.`when`(vMock[0x5]).thenReturn (33)
        Mockito.`when`(vMock[0x6]).thenReturn (48)
        Mockito.`when`(vMock[0x7]).thenReturn (27)
        Mockito.`when`(vMock[0x8]).thenReturn (72)
        Mockito.`when`(vMock[0x9]).thenReturn (88)
        Mockito.`when`(vMock[0xA]).thenReturn (123)
        Mockito.`when`(vMock[0xB]).thenReturn (111)
        Mockito.`when`(vMock[0xC]).thenReturn (108)
        Mockito.`when`(vMock[0xD]).thenReturn (128.toByte())
        Mockito.`when`(vMock[0xE]).thenReturn (130.toByte())

        val registers = Registers(vMock)
        registers.pc = 0x200

        shrVxVy(0x8016, registers)
        assertEquals(0x0200 + 2, registers.pc)

        shrVxVy(0x8126, registers)
        assertEquals(0x0200 + 4, registers.pc)

        shrVxVy(0x8236, registers)
        assertEquals(0x0200 + 6, registers.pc)

        shrVxVy(0x8346, registers)
        assertEquals(0x0200 + 8, registers.pc)

        shrVxVy(0x8456, registers)
        assertEquals(0x0200 + 10, registers.pc)

        shrVxVy(0x8566, registers)
        assertEquals(0x0200 + 12, registers.pc)

        shrVxVy(0x8676, registers)
        assertEquals(0x0200 + 14, registers.pc)

        shrVxVy(0x8786, registers)
        assertEquals(0x0200 + 16, registers.pc)

        shrVxVy(0x8896, registers)
        assertEquals(0x0200 + 18, registers.pc)

        shrVxVy(0x89A6, registers)
        assertEquals(0x0200 + 20, registers.pc)

        shrVxVy(0x8AB6, registers)
        assertEquals(0x0200 + 22, registers.pc)

        shrVxVy(0x8BC6, registers)
        assertEquals(0x0200 + 24, registers.pc)

        shrVxVy(0x8CD6, registers)
        assertEquals(0x0200 + 26, registers.pc)

        shrVxVy(0x8DE6, registers)
        assertEquals(0x0200 + 28, registers.pc)

        shrVxVy(0x8EF6, registers)
        assertEquals(0x0200 + 30, registers.pc)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x1] = 4

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 3

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = 12

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 24

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 13

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 36

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 44

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 61

        Mockito.verify(
                vMock,
                times(1)
        )[0xB] = 55

        Mockito.verify(
                vMock,
                times(1)
        )[0xC] = 54

        Mockito.verify(
                vMock,
        times(1)
        )[0xD] = 64

        Mockito.verify(
                vMock,
                times(1)
        )[0xE] = 65

        Mockito.verify(
                vMock,
                times(7)
        )[0xF] = 1

        Mockito.verify(
                vMock,
                times(8)
        )[0xF] = 0
    }

    @Test
    fun shlVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenAnswer { 16 }

        Mockito.`when`(vMock.size).thenReturn (16)
        Mockito.`when`(vMock[0x0]).thenReturn (0)
        Mockito.`when`(vMock[0x1]).thenReturn (8)
        Mockito.`when`(vMock[0x2]).thenReturn (7)
        Mockito.`when`(vMock[0x3]).thenReturn (24)
        Mockito.`when`(vMock[0x4]).thenReturn (32)
        Mockito.`when`(vMock[0x5]).thenReturn (33)
        Mockito.`when`(vMock[0x6]).thenReturn (48)
        Mockito.`when`(vMock[0x7]).thenReturn (27)
        Mockito.`when`(vMock[0x8]).thenReturn (72)
        Mockito.`when`(vMock[0x9]).thenReturn (88)
        Mockito.`when`(vMock[0xA]).thenReturn (123)
        Mockito.`when`(vMock[0xB]).thenReturn (111)
        Mockito.`when`(vMock[0xC]).thenReturn (108)
        Mockito.`when`(vMock[0xD]).thenReturn (128.toByte())
        Mockito.`when`(vMock[0xE]).thenReturn (130.toByte())

        val registers = Registers(vMock)
        registers.pc = 0x200

        shlVxVy(0x801E, registers)
        assertEquals(0x0200 + 2, registers.pc)

        shlVxVy(0x812E, registers)
        assertEquals(0x0200 + 4, registers.pc)

        shlVxVy(0x823E, registers)
        assertEquals(0x0200 + 6, registers.pc)

        shlVxVy(0x834E, registers)
        assertEquals(0x0200 + 8, registers.pc)

        shlVxVy(0x845E, registers)
        assertEquals(0x0200 + 10, registers.pc)

        shlVxVy(0x856E, registers)
        assertEquals(0x0200 + 12, registers.pc)

        shlVxVy(0x867E, registers)
        assertEquals(0x0200 + 14, registers.pc)

        shlVxVy(0x878E, registers)
        assertEquals(0x0200 + 16, registers.pc)

        shlVxVy(0x889E, registers)
        assertEquals(0x0200 + 18, registers.pc)

        shlVxVy(0x89AE, registers)
        assertEquals(0x0200 + 20, registers.pc)

        shlVxVy(0x8ABE, registers)
        assertEquals(0x0200 + 22, registers.pc)

        shlVxVy(0x8BCE, registers)
        assertEquals(0x0200 + 24, registers.pc)

        shlVxVy(0x8CDE, registers)
        assertEquals(0x0200 + 26, registers.pc)

        shlVxVy(0x8DEE, registers)
        assertEquals(0x0200 + 28, registers.pc)

        shlVxVy(0x8EFE, registers)
        assertEquals(0x0200 + 30, registers.pc)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x1] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 14

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = 48

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 64

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = 66

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 96

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 54

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 144.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 176.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 246.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xB] = 222.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xC] = 216.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xD] = 256.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0xE] = 260.toByte()

        Mockito.verify(
                vMock,
                times(7)
        )[0xF] = 1

        Mockito.verify(
                vMock,
                times(8)
        )[0xF] = 0
    }

}
