package es.chipeit.lib.io

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KeyboardTests {
    @Test
    fun keyDownAndUpTest() {
        val keyboard = Keyboard()

        assertEquals(0, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_0)
        assertEquals(0x0001, keyboard.keysDown)
        assertTrue(keyboard.isDown(0))

        keyboard.keyDown(Keyboard.Keys.KEY_1)
        assertEquals(0x0003, keyboard.keysDown)
        assertTrue(keyboard.isDown(1))

        keyboard.keyDown(Keyboard.Keys.KEY_2)
        assertEquals(0x0007, keyboard.keysDown)
        assertTrue(keyboard.isDown(2))

        keyboard.keyDown(Keyboard.Keys.KEY_3)
        assertEquals(0x000F, keyboard.keysDown)
        assertTrue(keyboard.isDown(3))

        keyboard.keyDown(Keyboard.Keys.KEY_4)
        assertEquals(0x001F, keyboard.keysDown)
        assertTrue(keyboard.isDown(4))

        keyboard.keyDown(Keyboard.Keys.KEY_5)
        assertEquals(0x003F, keyboard.keysDown)
        assertTrue(keyboard.isDown(5))

        keyboard.keyDown(Keyboard.Keys.KEY_6)
        assertEquals(0x007F, keyboard.keysDown)
        assertTrue(keyboard.isDown(6))

        keyboard.keyDown(Keyboard.Keys.KEY_7)
        assertEquals(0x00FF, keyboard.keysDown)
        assertTrue(keyboard.isDown(7))

        keyboard.keyDown(Keyboard.Keys.KEY_8)
        assertEquals(0x01FF, keyboard.keysDown)
        assertTrue(keyboard.isDown(8))

        keyboard.keyDown(Keyboard.Keys.KEY_9)
        assertEquals(0x03FF, keyboard.keysDown)
        assertTrue(keyboard.isDown(9))

        keyboard.keyDown(Keyboard.Keys.KEY_A)
        assertEquals(0x07FF, keyboard.keysDown)
        assertTrue(keyboard.isDown(10))

        keyboard.keyDown(Keyboard.Keys.KEY_B)
        assertEquals(0x0FFF, keyboard.keysDown)
        assertTrue(keyboard.isDown(11))

        keyboard.keyDown(Keyboard.Keys.KEY_C)
        assertEquals(0x1FFF, keyboard.keysDown)
        assertTrue(keyboard.isDown(12))

        keyboard.keyDown(Keyboard.Keys.KEY_D)
        assertEquals(0x3FFF, keyboard.keysDown)
        assertTrue(keyboard.isDown(13))

        keyboard.keyDown(Keyboard.Keys.KEY_E)
        assertEquals(0x7FFF, keyboard.keysDown)
        assertTrue(keyboard.isDown(14))

        keyboard.keyDown(Keyboard.Keys.KEY_F)
        assertEquals(0xFFFF, keyboard.keysDown)
        assertTrue(keyboard.isDown(15))

        keyboard.keyUp(Keyboard.Keys.KEY_0)
        assertEquals(0xFFFF and 0x0001.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(0))

        keyboard.keyUp(Keyboard.Keys.KEY_1)
        assertEquals(0xFFFF and 0x0003.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(1))

        keyboard.keyUp(Keyboard.Keys.KEY_2)
        assertEquals(0xFFFF and 0x0007.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(2))

        keyboard.keyUp(Keyboard.Keys.KEY_3)
        assertEquals(0xFFFF and 0x000F.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(3))

        keyboard.keyUp(Keyboard.Keys.KEY_4)
        assertEquals(0xFFFF and 0x001F.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(4))

        keyboard.keyUp(Keyboard.Keys.KEY_5)
        assertEquals(0xFFFF and 0x003F.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(5))

        keyboard.keyUp(Keyboard.Keys.KEY_6)
        assertEquals(0xFFFF and 0x007F.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(6))

        keyboard.keyUp(Keyboard.Keys.KEY_7)
        assertEquals(0xFFFF and 0x00FF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(7))

        keyboard.keyUp(Keyboard.Keys.KEY_8)
        assertEquals(0xFFFF and 0x01FF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(8))

        keyboard.keyUp(Keyboard.Keys.KEY_9)
        assertEquals(0xFFFF and 0x03FF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(9))

        keyboard.keyUp(Keyboard.Keys.KEY_A)
        assertEquals(0xFFFF and 0x07FF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(10))

        keyboard.keyUp(Keyboard.Keys.KEY_B)
        assertEquals(0xFFFF and 0x0FFF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(11))

        keyboard.keyUp(Keyboard.Keys.KEY_C)
        assertEquals(0xFFFF and 0x1FFF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(12))

        keyboard.keyUp(Keyboard.Keys.KEY_D)
        assertEquals(0xFFFF and 0x3FFF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(13))

        keyboard.keyUp(Keyboard.Keys.KEY_E)
        assertEquals(0xFFFF and 0x7FFF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(14))

        keyboard.keyUp(Keyboard.Keys.KEY_F)
        assertEquals(0xFFFF and 0xFFFF.inv(), keyboard.keysDown)
        assertFalse(keyboard.isDown(15))
    }
}
