@file:Suppress("UNCHECKED_CAST")

package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.Mockito
import org.mockito.BDDMockito.*
import org.mockito.Mockito.times
import org.mockito.Mockito.never

import es.chipeit.lib.interfaces.*
import es.chipeit.lib.io.IUserKeyboard

class InstructionSetTests {
    @Test
    fun clsTest() {
        val graphicMemoryMock = Mockito.mock(ICoreGraphicMemory::class.java)
        val registersMock = Mockito.mock(IRegisters::class.java)

        given(registersMock.pc).willReturn(0x200)

        cls(registersMock, graphicMemoryMock)

        then(graphicMemoryMock).should().clear()
        then(registersMock).should(times(1)).pc = 0x200 + 2
    }

    @Test
    fun retTest() {
        val registersMock = Mockito.mock(IRegisters::class.java)
        val stackMock = Mockito.mock(IMemory::class.java) as IMemory<Int>

        given(registersMock.sp).willReturn(1)
        given(stackMock[1]).willReturn(0x345)

        ret(registersMock, stackMock)

        then(registersMock).should(times(1)).sp = 0
        then(registersMock).should(times(1)).pc = 0x345

        given(registersMock.sp).willReturn(0)
        given(stackMock[0]).willReturn(0xFFF)

        ret(registersMock, stackMock)

        then(registersMock).should(times(1)).sp = -1
        then(registersMock).should(times(1)).pc = 0xFFF
    }

    @Test
    fun jpAddrTest() {
        val registersMock = Mockito.mock(IRegisters::class.java)

        jpAddr(0x1345, registersMock)

        then(registersMock).should(times(1)).pc = 0x345

        jpAddr(0x1FFF, registersMock)

        then(registersMock).should(times(1)).pc = 0xFFF
    }

    @Test
    fun callTest() {
        val registersMock = Mockito.mock(IRegisters::class.java)
        val stackMock = Mockito.mock(IMemory::class.java) as IMemory<Int>

        given(registersMock.sp).willReturn(-1)
        given(registersMock.pc).willReturn(0x200)

        call(0x2345, registersMock, stackMock)

        then(registersMock).should(times(1)).sp = 0
        then(registersMock).should(times(1)).pc = 0x345
        then(stackMock).should()[0] = 0x200

        given(registersMock.sp).willReturn(0)
        given(registersMock.pc).willReturn(0x345)

        call(0x2FFF, registersMock, stackMock)

        then(registersMock).should(times(1)).sp = 1
        then(registersMock).should(times(1)).pc = 0xFFF
        then(stackMock).should()[1] = 0x345
    }

    @Test
    fun seVxByteTest() {

    }

    @Test
    fun sneVxByteTest() {

    }

    @Test
    fun seVxVyTest() {

    }

    @Test
    fun ldVxByteTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)

        ldVxByte(0x6028, registersMock)

        then(vRegMock).should()[0x0] = 0x28
        then(registersMock).should().pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)

        ldVxByte(0x6EFF, registersMock)

        then(vRegMock).should()[0xE] = 0xFF.toByte()
        then(registersMock).should().pc = 0x202 + 2
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
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(127.toByte())
        given(vRegMock[0x1]).willReturn(128.toByte())

        addVxVy(0x8014, registersMock)

        then(vRegMock).should()[0x0] = 255.toByte()
        then(vRegMock).should()[0xF] = 0
        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(vRegMock[0x2]).willReturn(128.toByte())
        given(vRegMock[0x3]).willReturn(128.toByte())

        addVxVy(0x8234, registersMock)

        then(vRegMock).should()[0x2] = 0
        then(vRegMock).should()[0xF] = 1
        then(registersMock).should(times(1)).pc = 0x202 + 2
    }

    @Test
    fun subVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(255.toByte())
        given(vRegMock[0x1]).willReturn(254.toByte())

        subVxVy(0x8015, registersMock)

        then(vRegMock).should()[0x0] = 1
        then(vRegMock).should()[0xF] = 1
        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(vRegMock[0x2]).willReturn(254.toByte())
        given(vRegMock[0x3]).willReturn(255.toByte())

        subVxVy(0x8235, registersMock)

        then(vRegMock).should()[0x2] = 255.toByte()
        then(vRegMock).should()[0xF] = 0
        then(registersMock).should(times(1)).pc = 0x202 + 2
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
    fun subnVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(254.toByte())
        given(vRegMock[0x1]).willReturn(255.toByte())

        subnVxVy(0x8017, registersMock)

        then(vRegMock).should()[0x0] = 1
        then(vRegMock).should()[0xF] = 1
        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(vRegMock[0x2]).willReturn(255.toByte())
        given(vRegMock[0x3]).willReturn(254.toByte())

        subnVxVy(0x8237, registersMock)

        then(vRegMock).should()[0x2] = 255.toByte()
        then(vRegMock).should()[0xF] = 0
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

    @Test
    fun sneVxVyTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(5)
        given(vRegMock[0x1]).willReturn(5)

        sneVxVy(0x9010, registersMock)

        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(vRegMock[0x2]).willReturn(5)
        given(vRegMock[0x3]).willReturn(4)

        sneVxVy(0x9230, registersMock)

        then(registersMock).should(times(2)).pc = 0x202 + 2
    }

    @Test
    fun ldIAddrTest() {

    }

    @Test
    fun jpV0AddrTest() {

    }

    @Test
    fun rndVxByteTest() {

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
    fun skpVxTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        val keyboardMock = Mockito.mock(ICoreKeyboard::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(keyboardMock.isPressed(IUserKeyboard.Keys.KEY_5)).willReturn(true)
        given(vRegMock[0x0]).willReturn(5)

        sknpVx(0xE0A1, registersMock, keyboardMock)

        // Next instruction NOT SKIPPED
        then(registersMock).should(times(1)).pc += 2

        given(keyboardMock.isPressed(IUserKeyboard.Keys.KEY_A)).willReturn(false)
        given(vRegMock[0x1]).willReturn(0xA)

        sknpVx(0xE1A1, registersMock, keyboardMock)

        // Next instruction SKIPPED
        then(registersMock).should(times(3)).pc += 2
    }

    @Test
    fun sknpVxTest() {
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        val keyboardMock = Mockito.mock(ICoreKeyboard::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(keyboardMock.isPressed(IUserKeyboard.Keys.KEY_5)).willReturn(false)
        given(vRegMock[0x0]).willReturn(5)

        sknpVx(0xE0A1, registersMock, keyboardMock)

        // Next instruction SKIPPED
        then(registersMock).should(times(2)).pc += 2

        given(keyboardMock.isPressed(IUserKeyboard.Keys.KEY_A)).willReturn(true)
        given(vRegMock[0x1]).willReturn(0xA)

        sknpVx(0xE1A1, registersMock, keyboardMock)

        // Next instruction NOT SKIPPED
        then(registersMock).should(times(3)).pc += 2
    }

    @Test
    fun ldVxTimerTest() {
        val timerMock = Mockito.mock(ITimer::class.java)
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(timerMock.t).willReturn(4)

        ldVxTimer(0xF007, registersMock, timerMock)

        then(vRegMock).should()[0x0] = 4
        then(registersMock).should(times(1)).pc = 0x200 + 2
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
    fun ldTimerVxTest() {
        val timerMock = Mockito.mock(ITimer::class.java)
        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(vRegMock[0x0]).willReturn(4)

        ldTimerVx(0xF015, registersMock, timerMock)

        then(timerMock).should().t = 4
        then(registersMock).should(times(1)).pc = 0x200 + 2

        given(registersMock.pc).willReturn(0x202)
        given(vRegMock[0x1]).willReturn(8)

        ldTimerVx(0xF118, registersMock, timerMock)

        then(timerMock).should().t = 8
        then(registersMock).should(times(1)).pc = 0x202 + 2
    }

    @Test
    fun addIVxTest() {

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
    fun ldIVxTest() {
        val memoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(registersMock.i).willReturn(0x300)

        given(vRegMock[0x0]).willReturn(15)
        given(vRegMock[0x1]).willReturn(16)
        given(vRegMock[0x2]).willReturn(17)
        given(vRegMock[0x3]).willReturn(18)
        given(vRegMock[0x4]).willReturn(19)
        given(vRegMock[0x5]).willReturn(20)
        given(vRegMock[0x6]).willReturn(21)
        given(vRegMock[0x7]).willReturn(22)
        given(vRegMock[0x8]).willReturn(23)
        given(vRegMock[0x9]).willReturn(24)
        given(vRegMock[0xA]).willReturn(25)
        given(vRegMock[0xB]).willReturn(26)
        given(vRegMock[0xC]).willReturn(27)
        given(vRegMock[0xD]).willReturn(28)
        given(vRegMock[0xE]).willReturn(29)

        ldIVx(0xFE55, registersMock, memoryMock)

        then(memoryMock).should(times(1))[0x300] = 15
        then(memoryMock).should(times(1))[0x301] = 16
        then(memoryMock).should(times(1))[0x302] = 17
        then(memoryMock).should(times(1))[0x303] = 18
        then(memoryMock).should(times(1))[0x304] = 19
        then(memoryMock).should(times(1))[0x305] = 20
        then(memoryMock).should(times(1))[0x306] = 21
        then(memoryMock).should(times(1))[0x307] = 22
        then(memoryMock).should(times(1))[0x308] = 23
        then(memoryMock).should(times(1))[0x309] = 24
        then(memoryMock).should(times(1))[0x30A] = 25
        then(memoryMock).should(times(1))[0x30B] = 26
        then(memoryMock).should(times(1))[0x30C] = 27
        then(memoryMock).should(times(1))[0x30D] = 28
        then(memoryMock).should(times(1))[0x30E] = 29
        then(registersMock).should(times(1)).pc = 0x200 + 2
    }

    @Test
    fun ldVxITest() {
        val memoryMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>

        val vRegMock = Mockito.mock(IMemory::class.java) as IMemory<Byte>
        val registersMock = Mockito.mock(IRegisters::class.java)
        given(registersMock.v).willReturn(vRegMock)

        given(registersMock.pc).willReturn(0x200)
        given(registersMock.i).willReturn(0x300)

        given(memoryMock[0x300]).willReturn(15)
        given(memoryMock[0x301]).willReturn(16)
        given(memoryMock[0x302]).willReturn(17)
        given(memoryMock[0x303]).willReturn(18)
        given(memoryMock[0x304]).willReturn(19)
        given(memoryMock[0x305]).willReturn(20)
        given(memoryMock[0x306]).willReturn(21)
        given(memoryMock[0x307]).willReturn(22)
        given(memoryMock[0x308]).willReturn(23)
        given(memoryMock[0x309]).willReturn(24)
        given(memoryMock[0x30A]).willReturn(25)
        given(memoryMock[0x30B]).willReturn(26)
        given(memoryMock[0x30C]).willReturn(27)
        given(memoryMock[0x30D]).willReturn(28)
        given(memoryMock[0x30E]).willReturn(29)

        ldVxI(0xFE65, registersMock, memoryMock)

        then(vRegMock).should(times(1))[0x0] = 15
        then(vRegMock).should(times(1))[0x1] = 16
        then(vRegMock).should(times(1))[0x2] = 17
        then(vRegMock).should(times(1))[0x3] = 18
        then(vRegMock).should(times(1))[0x4] = 19
        then(vRegMock).should(times(1))[0x5] = 20
        then(vRegMock).should(times(1))[0x6] = 21
        then(vRegMock).should(times(1))[0x7] = 22
        then(vRegMock).should(times(1))[0x8] = 23
        then(vRegMock).should(times(1))[0x9] = 24
        then(vRegMock).should(times(1))[0xA] = 25
        then(vRegMock).should(times(1))[0xB] = 26
        then(vRegMock).should(times(1))[0xC] = 27
        then(vRegMock).should(times(1))[0xD] = 28
        then(vRegMock).should(times(1))[0xE] = 29
        then(registersMock).should(times(1)).pc = 0x200 + 2
    }
}
