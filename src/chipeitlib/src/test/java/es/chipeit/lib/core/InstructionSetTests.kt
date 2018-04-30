package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.invocation.InvocationOnMock
import org.mockito.Mockito
import org.mockito.BDDMockito.*
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.times
import org.mockito.Mockito.never
import org.mockito.stubbing.Answer

import es.chipeit.lib.interfaces.ICoreGraphicMemory
import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters
import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.IUserKeyboard

class InstructionSetTests {
    @Test
    fun clsTest() {
        val graphicMemory = Mockito.mock(ICoreGraphicMemory::class.java)
        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.pc = 0x200

        cls(registers, graphicMemory)

        assertEquals(0x200 + 2, registers.pc)
        Mockito.verify(
                graphicMemory,
                times(1)
        ).clear()
    }

    @Test
    fun retTest() {
        val registers = Registers(ByteMemory(ByteArray(16)))
        val stackMock = Mockito.mock(IMemory::class.java) as IMemory<Int>

        Mockito.`when`(stackMock[2]).thenReturn(0x222)
        Mockito.`when`(stackMock[1]).thenReturn(0x456)
        Mockito.`when`(stackMock[0]).thenReturn(0x789)

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
        ).pc = 0x225

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x512

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0xFFF
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
        val registersMemoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(registersMemoryMock.size).thenReturn(16)

        val registersMock = Registers(registersMemoryMock)
        registersMock.pc = 0x200

        ldVxByte(0x6011, registersMock)
        assertEquals(0x200 + 2, registersMock.pc)

        ldVxByte(0x6122, registersMock)
        assertEquals(0x200 + 4, registersMock.pc)

        ldVxByte(0x6233, registersMock)
        assertEquals(0x200 + 6, registersMock.pc)

        ldVxByte(0x6344, registersMock)
        assertEquals(0x200 + 8, registersMock.pc)

        ldVxByte(0x6455, registersMock)
        assertEquals(0x200 + 10, registersMock.pc)

        ldVxByte(0x6566, registersMock)
        assertEquals(0x200 + 12, registersMock.pc)

        ldVxByte(0x6677, registersMock)
        assertEquals(0x200 + 14, registersMock.pc)

        ldVxByte(0x6788, registersMock)
        assertEquals(0x200 + 16, registersMock.pc)

        ldVxByte(0x6899, registersMock)
        assertEquals(0x200 + 18, registersMock.pc)

        ldVxByte(0x69AA, registersMock)
        assertEquals(0x200 + 20, registersMock.pc)

        ldVxByte(0x6ABB, registersMock)
        assertEquals(0x200 + 22, registersMock.pc)

        ldVxByte(0x6BCC, registersMock)
        assertEquals(0x200 + 24, registersMock.pc)

        ldVxByte(0x6CDD, registersMock)
        assertEquals(0x200 + 26, registersMock.pc)

        ldVxByte(0x6DEE, registersMock)
        assertEquals(0x200 + 28, registersMock.pc)

        ldVxByte(0x6EFF, registersMock)
        assertEquals(0x200 + 30, registersMock.pc)

        ldVxByte(0x6F00, registersMock)
        assertEquals(0x200 + 32, registersMock.pc)

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x0] = 0x11

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x1] = 0x22

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x2] = 0x33

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x3] = 0x44

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x4] = 0x55

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x5] = 0x66

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x6] = 0x77

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x7] = 0x88.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x8] = 0x99.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0x9] = 0xAA.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xA] = 0xBB.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xB] = 0xCC.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xC] = 0xDD.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xD] = 0xEE.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xE] = 0xFF.toByte()

        Mockito.verify(
                registersMemoryMock,
                times(1)
        )[0xF] = 0x00
    }

    @Test
    fun skpVxTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenReturn(16)
        Mockito.`when`(vMock[0xA]).thenReturn(3)
        Mockito.`when`(vMock[0xB]).thenReturn(8)

        val soundTimerMock = Mockito.mock(ITimer::class.java)

        val keyboard = Keyboard(soundTimerMock)
        keyboard.pressKey(IUserKeyboard.Keys.KEY_3)
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_8)

        val registersMock = Registers(vMock)
        registersMock.pc = 0x200

        skpVx(0xEA9E, registersMock, keyboard)

        // Next instruction was skipped
        assertEquals(0x200 + 4, registersMock.pc)
        Mockito.verify(
                vMock,
                times(1)
        )[0xA]

        skpVx(0xEB9E, registersMock, keyboard)

        // Next instruction was not skipped
        assertEquals(0x204 + 2, registersMock.pc)
        Mockito.verify(
                vMock,
                times(1)
        )[0xB]
    }

    @Test
    fun sknpVxTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vMock.size).thenReturn(16)
        Mockito.`when`(vMock[0xA]).thenReturn(3)
        Mockito.`when`(vMock[0xB]).thenReturn(8)

        val soundTimerMock = Mockito.mock(ITimer::class.java)

        val keyboard = Keyboard(soundTimerMock)
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_3)
        keyboard.pressKey(IUserKeyboard.Keys.KEY_8)

        val registersMock = Registers(vMock)
        registersMock.pc = 0x200

        sknpVx(0xEA9E, registersMock, keyboard)

        // Next instruction was skipped
        assertEquals(0x200 + 4, registersMock.pc)
        Mockito.verify(
                vMock,
                times(1)
        )[0xA]

        sknpVx(0xEB9E, registersMock, keyboard)

        // Next instruction was not skipped
        assertEquals(0x204 + 2, registersMock.pc)
        Mockito.verify(
                vMock,
                times(1)
        )[0xB]
    }

    @Test
    fun ldVxKTest() {
        val vRegsMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        Mockito.`when`(vRegsMock.size).thenReturn(16)

        val registers = Registers(vRegsMock)
        registers.pc = 0x200

        var isSoundTimerActive = true

        val soundTimerMock = Mockito.mock(ITimer::class.java)
        Mockito.`when`(soundTimerMock.isActive()).then { isSoundTimerActive }

        val keyboard = Keyboard(soundTimerMock)
        val instruction = 0xF70A

        assertFalse(keyboard.isCapturingNextKeyRelease)

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, never()).t = 4
        Mockito.verify(soundTimerMock, never()).isActive()

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, never()).t = 4
        Mockito.verify(soundTimerMock, never()).isActive()

        keyboard.pressKey(IUserKeyboard.Keys.KEY_8)
        keyboard.pressKey(IUserKeyboard.Keys.KEY_5)
        Mockito.verify(soundTimerMock, times(2)).t = 4

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, times(3)).t = 4
        Mockito.verify(soundTimerMock, never()).isActive()

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, times(4)).t = 4
        Mockito.verify(soundTimerMock, never()).isActive()

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_5)
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_8)

        assertEquals(IUserKeyboard.Keys.KEY_5, keyboard.capturedKeyRelease)

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.KEY_5, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, times(4)).t = 4
        Mockito.verify(soundTimerMock, times(1)).isActive()

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.KEY_5, keyboard.capturedKeyRelease)
        assertEquals(0x200, registers.pc)
        Mockito.verify(soundTimerMock, times(4)).t = 4
        Mockito.verify(soundTimerMock, times(2)).isActive()

        isSoundTimerActive = false

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200 + 2, registers.pc)
        Mockito.verify(
                vRegsMock,
                times(1)
        )[0x7] = IUserKeyboard.Keys.KEY_5.data.id
        Mockito.verify(soundTimerMock, times(3)).isActive()

        isSoundTimerActive = true

        ldVxK(instruction, registers, keyboard)

        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200 + 2, registers.pc)
        Mockito.verify(soundTimerMock, times(4)).t = 4
        Mockito.verify(soundTimerMock, times(3)).isActive()

        keyboard.pressKey(IUserKeyboard.Keys.KEY_8)
        keyboard.pressKey(IUserKeyboard.Keys.KEY_5)
        Mockito.verify(soundTimerMock, times(6)).t = 4

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_8)
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_5)

        assertEquals(IUserKeyboard.Keys.KEY_8, keyboard.capturedKeyRelease)

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.KEY_8, keyboard.capturedKeyRelease)
        assertEquals(0x200 + 2, registers.pc)
        Mockito.verify(soundTimerMock, times(6)).t = 4
        Mockito.verify(soundTimerMock, times(4)).isActive()

        isSoundTimerActive = false

        ldVxK(instruction, registers, keyboard)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)
        assertEquals(0x200 + 4, registers.pc)
        Mockito.verify(
                vRegsMock,
                times(1)
        )[0x7] = IUserKeyboard.Keys.KEY_8.data.id
        Mockito.verify(soundTimerMock, times(5)).isActive()
    }

    @Test
    fun addVxByteTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(254.toByte())

        addVxByte(0x7001, registersMock)

        then(vRegMock).should()[0x0] = 255.toByte()
        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(vRegMock[0x1]).willReturn(200.toByte())

        addVxByte(0x7138, registersMock)

        then(vRegMock).should()[0x1] = 0
        then(registersMock).should(times(1)).pc = 0x202 + 2
    }

    @Test
    fun ldVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x1]).willReturn(255.toByte())

        ldVxVy(0x8010, registersMock)

        then(vRegMock).should()[0x0] = 255.toByte()
        then(registersMock).should(times(1)).pc = 0x200 + 2
    }

    @Test
    fun orVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(0x5C)          // 0101 1100
        given(vRegMock[0x1]).willReturn(0xA3.toByte()) // 1010 0011

        orVxVy(0x8011, registersMock)

        then(vRegMock).should()[0x0] = 0xFF.toByte()
        then(registersMock).should(times(1)).pc = 0x200 + 2
    }

    @Test
    fun addVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(0)
        Mockito.`when`(vMock[0x1]).thenReturn(127)

        Mockito.`when`(vMock[0x2]).thenReturn(32)

        Mockito.`when`(vMock[0x3]).thenReturn(1)
        Mockito.`when`(vMock[0x4]).thenReturn(127)

        Mockito.`when`(vMock[0x5]).thenReturn(0)
        Mockito.`when`(vMock[0x6]).thenReturn(128.toByte())

        Mockito.`when`(vMock[0x7]).thenReturn(255.toByte())
        Mockito.`when`(vMock[0x8]).thenReturn(255.toByte())

        Mockito.`when`(vMock[0x9]).thenReturn(255.toByte())
        Mockito.`when`(vMock[0xA]).thenReturn(1)

        val registers = Registers(vMock)
        registers.pc = 0x200

        addVxVy(0x8014, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 127

        Mockito.verify(
                vMock,
                times(1)
        )[0x0]

        Mockito.verify(
                vMock,
                times(1)
        )[0x1]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 0

        addVxVy(0x8104, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x1] = 127

        Mockito.verify(
                vMock,
                times(2)
        )[0x0]

        Mockito.verify(
                vMock,
                times(2)
        )[0x1]

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 0

        addVxVy(0x8224, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 64

        Mockito.verify(
                vMock,
                times(2)
        )[0x2]

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 0

        addVxVy(0x8344, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x3] = 128.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x3]

        Mockito.verify(
                vMock,
                times(1)
        )[0x4]

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 0

        addVxVy(0x8564, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x5] = 128.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x5]

        Mockito.verify(
                vMock,
                times(1)
        )[0x6]

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 0

        addVxVy(0x8784, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 254.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x7]

        Mockito.verify(
                vMock,
                times(1)
        )[0x8]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 1

        addVxVy(0x89A4, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x9]

        Mockito.verify(
                vMock,
                times(1)
        )[0xA]

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 1

        assertEquals(0x200 + 7 * 0x2, registers.pc)
    }

    @Test
    fun subVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(32)
        Mockito.`when`(vMock[0x1]).thenReturn(16)

        Mockito.`when`(vMock[0x2]).thenReturn(32)
        Mockito.`when`(vMock[0x3]).thenReturn(33)

        Mockito.`when`(vMock[0x4]).thenReturn(32)
        Mockito.`when`(vMock[0x5]).thenReturn(32)

        Mockito.`when`(vMock[0x6]).thenReturn(32)

        Mockito.`when`(vMock[0x7]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x8]).thenReturn(255.toByte())
        Mockito.`when`(vMock[0x9]).thenReturn(0)
        Mockito.`when`(vMock[0xA]).thenReturn(1)

        val registers = Registers(vMock)
        registers.pc = 0x200

        subVxVy(0x8015, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x0]

        Mockito.verify(
                vMock,
                times(1)
        )[0x1]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 1

        subVxVy(0x8235, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 255.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x2]

        Mockito.verify(
                vMock,
                times(1)
        )[0x3]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 0

        subVxVy(0x8455, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x4]

        Mockito.verify(
                vMock,
                times(1)
        )[0x5]

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 0

        subVxVy(0x8665, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 0

        Mockito.verify(
                vMock,
                times(2)
        )[0x6]

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 0

        subVxVy(0x8795, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 128.toByte()

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 1

        subVxVy(0x8975, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 128.toByte()

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 0

        subVxVy(0x87A5, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 127

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 1

        subVxVy(0x8A75, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 129.toByte()

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 0

        subVxVy(0x8895, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 255.toByte()

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 1

        subVxVy(0x8985, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 1

        Mockito.verify(
                vMock,
                times(6)
        )[0xF] = 0

        subVxVy(0x88A5, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 254.toByte()

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 1

        subVxVy(0x8A85, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 2

        Mockito.verify(
                vMock,
                times(7)
        )[0xF] = 0

        Mockito.verify(
                vMock,
                times(4)
        )[0x7]

        Mockito.verify(
                vMock,
                times(4)
        )[0x8]

        Mockito.verify(
                vMock,
                times(4)
        )[0x9]

        Mockito.verify(
                vMock,
                times(4)
        )[0xA]

        assertEquals(0x200 + 12 * 0x2, registers.pc)
    }

    @Test
    fun subnVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(16)
        Mockito.`when`(vMock[0x1]).thenReturn(32)

        Mockito.`when`(vMock[0x2]).thenReturn(33)
        Mockito.`when`(vMock[0x3]).thenReturn(32)

        Mockito.`when`(vMock[0x4]).thenReturn(32)
        Mockito.`when`(vMock[0x5]).thenReturn(32)

        Mockito.`when`(vMock[0x6]).thenReturn(32)

        Mockito.`when`(vMock[0x7]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x8]).thenReturn(255.toByte())
        Mockito.`when`(vMock[0x9]).thenReturn(0)
        Mockito.`when`(vMock[0xA]).thenReturn(1)

        val registers = Registers(vMock)
        registers.pc = 0x200

        subnVxVy(0x8017, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x0] = 16

        Mockito.verify(
                vMock,
                times(1)
        )[0x0]

        Mockito.verify(
                vMock,
                times(1)
        )[0x1]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 1

        subnVxVy(0x8237, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x2] = 255.toByte()

        Mockito.verify(
                vMock,
                times(1)
        )[0x2]

        Mockito.verify(
                vMock,
                times(1)
        )[0x3]

        Mockito.verify(
                vMock,
                times(1)
        )[0xF] = 0

        subnVxVy(0x8457, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x4] = 0

        Mockito.verify(
                vMock,
                times(1)
        )[0x4]

        Mockito.verify(
                vMock,
                times(1)
        )[0x5]

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 0

        subnVxVy(0x8667, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x6] = 0

        Mockito.verify(
                vMock,
                times(2)
        )[0x6]

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 0

        subnVxVy(0x8797, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 128.toByte()

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 0

        subnVxVy(0x8977, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 128.toByte()

        Mockito.verify(
                vMock,
                times(2)
        )[0xF] = 1

        subnVxVy(0x87A7, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x7] = 129.toByte()

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 0

        subnVxVy(0x8A77, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 127

        Mockito.verify(
                vMock,
                times(3)
        )[0xF] = 1

        subnVxVy(0x8897, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 1

        Mockito.verify(
                vMock,
                times(6)
        )[0xF] = 0

        subnVxVy(0x8987, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x9] = 255.toByte()

        Mockito.verify(
                vMock,
                times(4)
        )[0xF] = 1

        subnVxVy(0x88A7, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0x8] = 2

        Mockito.verify(
                vMock,
                times(7)
        )[0xF] = 0

        subnVxVy(0x8A87, registers)

        Mockito.verify(
                vMock,
                times(1)
        )[0xA] = 254.toByte()

        Mockito.verify(
                vMock,
                times(5)
        )[0xF] = 1

        Mockito.verify(
                vMock,
                times(4)
        )[0x7]

        Mockito.verify(
                vMock,
                times(4)
        )[0x8]

        Mockito.verify(
                vMock,
                times(4)
        )[0x9]

        Mockito.verify(
                vMock,
                times(4)
        )[0xA]

        assertEquals(0x200 + 12 * 0x2, registers.pc)
    }

    @Test
    fun sneVxVyTest() {
        val vMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        Mockito.`when`(vMock.size).thenReturn(16)

        Mockito.`when`(vMock[0x0]).thenReturn(0)
        Mockito.`when`(vMock[0x1]).thenReturn(0)

        Mockito.`when`(vMock[0x2]).thenReturn(32)
        Mockito.`when`(vMock[0x3]).thenReturn(32)

        Mockito.`when`(vMock[0x4]).thenReturn(64)
        Mockito.`when`(vMock[0x5]).thenReturn(32)

        Mockito.`when`(vMock[0x6]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x7]).thenReturn(128.toByte())

        Mockito.`when`(vMock[0x8]).thenReturn(128.toByte())
        Mockito.`when`(vMock[0x9]).thenReturn(129.toByte())

        Mockito.`when`(vMock[0xA]).thenReturn(2)
        Mockito.`when`(vMock[0xB]).thenReturn(128.toByte())

        val registers = Registers(vMock)
        registers.pc = 0x200

        sneVxVy(0x9010, registers)
        assertEquals(0x200 + 1 * 0x2, registers.pc)

        sneVxVy(0x9230, registers)
        assertEquals(0x200 + 2 * 0x2, registers.pc)

        sneVxVy(0x9450, registers)
        assertEquals(0x200 + 4 * 0x2, registers.pc)

        sneVxVy(0x9670, registers)
        assertEquals(0x200 + 5 * 0x2, registers.pc)

        sneVxVy(0x9890, registers)
        assertEquals(0x200 + 7 * 0x2, registers.pc)

        sneVxVy(0x9AB0, registers)
        assertEquals(0x200 + 9 * 0x2, registers.pc)
    }

    @Test
    fun drwVxVyNibbleTest() {
        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.v[0] = 4
        registers.v[1] = 3

        registers.pc = 0x200
        registers.i = 0x02

        val memory = ByteMemory(ByteArray(4))

        // Not use.
        memory[0] = 0xAA.toByte() // 1010 1010b
        memory[1] = 0xAA.toByte() // 1010 1010b

        // Sprite from here.
        memory[2] = 0x7F.toByte() // 0111 1111b
        memory[3] = 0xFE.toByte() // 1111 1110b

        val rawMatrix = Array(4) { Array(8) { false } }
        val graphicMemory = GraphicMemory(rawMatrix)

        drwVxVyNibble(0xD010, registers, memory, graphicMemory)
        for (column in rawMatrix)
            for (cell in column)
                assertEquals(false, cell)

        /*
            1110 1111b = 0xEF
            0000 0000b
            0000 0000b
            1111 0111b = 0xF7
        */
        val expectedMatrix = arrayOf(
                arrayOf(true, true, true, false, true, true, true, true),
                arrayOf(false, false, false, false, false, false, false, false),
                arrayOf(false, false, false, false, false, false, false, false),
                arrayOf(true, true, true, true, false, true, true, true)
        )

        drwVxVyNibble(0xD012, registers, memory, graphicMemory)
        assertTrue { rawMatrix contentDeepEquals expectedMatrix }

        assertEquals(0x200 + 2 * 0x2, registers.pc)
    }

    @Test
    fun drwVxVyNibbleWithoutClearTest() {
        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.v[0] = 0
        registers.v[1] = 0

        registers.pc = 0x200
        registers.i = 0x00

        val memory = ByteMemory(byteArrayOf(
                0x00,
                0x00,
                0xFF.toByte()
        ))

        val rawMatrix = Array(memory.size) { Array(8) { false } }
        val graphicMemory = GraphicMemory(rawMatrix)

        // Drawing a zero-height sprite will not clear a pixel.
        registers.v[0xF] = 1
        drwVxVyNibble(0xD010, registers, memory, graphicMemory)
        assertEquals(0x00, registers.v[0xF])

        /*
            If no pixel is cleared, VF must be set to zero. (Don't use 0xFF xor 0xFF!)
            Graphic memory xor sprite notation.
            0x00 xor 0x00 -> 0x00
            0xFF xor 0x00 -> 0xFF
            0x00 xor 0xFF -> 0xFF
         */
        for (column in rawMatrix)
            for (cell in column)
                assertEquals(false, cell)

        val j = 1
        for (i in 0 until rawMatrix[j].size)
            rawMatrix[j][i] = true

        registers.v[0xF] = 1
        drwVxVyNibble(0xD010 or memory.size, registers, memory, graphicMemory)
        assertEquals(0x00, registers.v[0xF])
    }

    @Test
    fun drwVxVyNibbleWithClearTest() {
        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.v[0] = 0
        registers.v[1] = 0

        registers.pc = 0x200
        registers.i = 0x00

        val memory = ByteMemory(byteArrayOf(
                0xFF.toByte(),
                0x00
        ))

        val rawMatrix = Array(memory.size) { Array(8) { false } }
        val graphicMemory = GraphicMemory(rawMatrix)

        /*
            If a pixel is cleared, VF must be set to one even if it was not in the last row.
            Graphic memory xor sprite notation.
            0xFF xor 0xFF -> 0x00
            0x00 xor 0x00 -> 0x00
         */
        val j = 0
        for (i in 0 until rawMatrix[j].size)
            rawMatrix[j][i] = true

        registers.v[0xF] = 0
        drwVxVyNibble(0xD010 or memory.size, registers, memory, graphicMemory)
        assertEquals(0x01, registers.v[0xF])
    }

    @Test
    fun ldVxTimerTest() {
        val tAnswer = object : Answer<Byte> {
            var t: Byte = 0

            override fun answer(invocation: InvocationOnMock?): Byte {
                return t
            }
        }

        val timerMock = Mockito.mock(ITimer::class.java)
        Mockito.`when`(timerMock.t).thenAnswer(tAnswer)

        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.pc = 0x200

        assertEquals(0, registers.v[0x0])

        ldVxTimer(0xF007, registers, timerMock)
        assertEquals(0, registers.v[0x0])

        tAnswer.t = 50

        ldVxTimer(0xF007, registers, timerMock)
        assertEquals(50, registers.v[0x0])

        tAnswer.t = 255.toByte()

        ldVxTimer(0xF007, registers, timerMock)
        assertEquals(255.toByte(), registers.v[0x0])

        tAnswer.t = 128.toByte()

        ldVxTimer(0xF107, registers, timerMock)
        assertEquals(255.toByte(), registers.v[0x0])
        assertEquals(128.toByte(), registers.v[0x1])

        assertEquals(0x200 + 4 * 0x2, registers.pc)
    }

    @Test
    fun ldTimerVxTest() {
        val timerMock = Mockito.mock(ITimer::class.java)
        val registers = Registers(ByteMemory(ByteArray(16)))

        registers.pc = 0x200

        assertEquals(0, registers.v[0x0])

        ldTimerVx(0xF015, registers, timerMock)
        assertEquals(0, registers.v[0x0])
        Mockito.verify(
                timerMock,
                times(1)
        ).t = 0

        registers.v[0x0] = 50

        ldTimerVx(0xF015, registers, timerMock)
        assertEquals(50, registers.v[0x0])
        Mockito.verify(
                timerMock,
                times(1)
        ).t = 50

        registers.v[0x1] = 80

        ldTimerVx(0xF115, registers, timerMock)
        assertEquals(50, registers.v[0x0])
        assertEquals(80, registers.v[0x1])
        Mockito.verify(
                timerMock,
                times(1)
        ).t = 80

        assertEquals(0x200 + 3 * 0x2, registers.pc)
    }

    @Test
    fun ldFVxTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)

        for (i in 0x0..0xF) {
            given(vRegMock[i]).willReturn(i.toByte())

            ldFVx(0xF029 or (i shl 2 * 4), registersMock)

            then(registersMock).should().i =
                    Constants.HexFontStart + i * Constants.CharacterSpriteLength
            then(registersMock).should(times(i + 1)).pc = 0x200 + 2
        }
    }

    @Test
    fun ldBVxTest() {
        val memoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(registersMock.i).willReturn(0x300)

        given(vRegMock[0]).willReturn(123.toByte())

        ldBVx(0xF033, registersMock, memoryMock)

        then(memoryMock).should()[0x300] = 1
        then(memoryMock).should()[0x301] = 2
        then(memoryMock).should()[0x302] = 3
        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(registersMock.i).willReturn(0x400)

        given(vRegMock[1]).willReturn(255.toByte())

        ldBVx(0xF133, registersMock, memoryMock)

        then(memoryMock).should()[0x400] = 2
        then(memoryMock).should()[0x401] = 5
        then(memoryMock).should()[0x402] = 5
        then(registersMock).should(times(1)).pc = 0x202 + 2
    }

    @Test
    fun andVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(0x5C) // 0101 1100
        given(vRegMock[0x1]).willReturn(0x53) // 0101 0011

        andVxVy(0x8013, registersMock)

        then(vRegMock).should()[0x0] = 0x50   // 0101 0000
        then(registersMock).should(times(1)).pc = 0x200 + 2
    }

    @Test
    fun xorVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(0x5C) // 0101 1100
        given(vRegMock[0x1]).willReturn(0x53) // 0101 0011

        xorVxVy(0x8013, registersMock)

        then(vRegMock).should()[0x0] = 0x0F   // 0000 1111
        then(registersMock).should(times(1)).pc = 0x200 + 2
    }

    @Test
    fun shrVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x1]).willReturn(0x02)

        shrVxVy(0x801E, registersMock)

        then(vRegMock).should()[0x0] = 1
        then(vRegMock).should()[0xF] = 0
        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(vRegMock[0x3]).willReturn(0x03)

        shrVxVy(0x823E, registersMock)

        then(vRegMock).should()[0x2] = 1
        then(vRegMock).should()[0xF] = 1
        then(registersMock).should(times(1)).pc = 0x202 + 2
    }

    @Test
    fun shlVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x1]).willReturn(0x01)

        shlVxVy(0x801E, registersMock)

        then(vRegMock).should()[0x0] = 2
        then(vRegMock).should()[0xF] = 0
        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(vRegMock[0x3]).willReturn(0x80.toByte())

        shlVxVy(0x823E, registersMock)

        then(vRegMock).should()[0x2] = 0
        then(vRegMock).should()[0xF] = 1
        then(registersMock).should(times(1)).pc = 0x202 + 2
    }
}
