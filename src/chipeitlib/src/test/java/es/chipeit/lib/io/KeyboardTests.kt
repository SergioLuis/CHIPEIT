package es.chipeit.lib.io

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class KeyboardTests {
    @Test
    fun keyDownAndUpTest() {
        val keyboard = Keyboard()

        keyboard.pressKey(Keyboard.Keys.KEY_0)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_0))

        keyboard.pressKey(Keyboard.Keys.KEY_1)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_1))

        keyboard.pressKey(Keyboard.Keys.KEY_2)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_2))

        keyboard.pressKey(Keyboard.Keys.KEY_3)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_3))

        keyboard.pressKey(Keyboard.Keys.KEY_4)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_4))

        keyboard.pressKey(Keyboard.Keys.KEY_5)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_5))

        keyboard.pressKey(Keyboard.Keys.KEY_6)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_6))

        keyboard.pressKey(Keyboard.Keys.KEY_7)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_7))

        keyboard.pressKey(Keyboard.Keys.KEY_8)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_8))

        keyboard.pressKey(Keyboard.Keys.KEY_9)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_9))

        keyboard.pressKey(Keyboard.Keys.KEY_A)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_A))

        keyboard.pressKey(Keyboard.Keys.KEY_B)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_B))

        keyboard.pressKey(Keyboard.Keys.KEY_C)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_C))

        keyboard.pressKey(Keyboard.Keys.KEY_D)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_D))

        keyboard.pressKey(Keyboard.Keys.KEY_E)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_E))

        keyboard.pressKey(Keyboard.Keys.KEY_F)
        assertTrue(keyboard.isPressed(Keyboard.Keys.KEY_F))

        keyboard.releaseKey(Keyboard.Keys.KEY_0)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_0))

        keyboard.releaseKey(Keyboard.Keys.KEY_1)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_1))

        keyboard.releaseKey(Keyboard.Keys.KEY_2)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_2))

        keyboard.releaseKey(Keyboard.Keys.KEY_3)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_3))

        keyboard.releaseKey(Keyboard.Keys.KEY_4)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_4))

        keyboard.releaseKey(Keyboard.Keys.KEY_5)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_5))

        keyboard.releaseKey(Keyboard.Keys.KEY_6)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_6))

        keyboard.releaseKey(Keyboard.Keys.KEY_7)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_7))

        keyboard.releaseKey(Keyboard.Keys.KEY_8)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_8))

        keyboard.releaseKey(Keyboard.Keys.KEY_9)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_9))

        keyboard.releaseKey(Keyboard.Keys.KEY_A)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_A))

        keyboard.releaseKey(Keyboard.Keys.KEY_B)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_B))

        keyboard.releaseKey(Keyboard.Keys.KEY_C)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_C))

        keyboard.releaseKey(Keyboard.Keys.KEY_D)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_D))

        keyboard.releaseKey(Keyboard.Keys.KEY_E)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_E))

        keyboard.releaseKey(Keyboard.Keys.KEY_F)
        assertFalse(keyboard.isPressed(Keyboard.Keys.KEY_F))
    }

    @Test
    fun lastKeyReleasedTest() {
        val keyboard = Keyboard()

        assertEquals(Keyboard.Keys.NONE, keyboard.lastKeyReleased)

        keyboard.pressKey(Keyboard.Keys.KEY_8)

        assertEquals(Keyboard.Keys.NONE, keyboard.lastKeyReleased)

        keyboard.pressKey(Keyboard.Keys.KEY_5)

        assertEquals(Keyboard.Keys.NONE, keyboard.lastKeyReleased)

        keyboard.releaseKey(Keyboard.Keys.KEY_0)

        assertEquals(Keyboard.Keys.NONE, keyboard.lastKeyReleased)

        keyboard.releaseKey(Keyboard.Keys.KEY_5)

        assertEquals(Keyboard.Keys.KEY_5, keyboard.lastKeyReleased)

        keyboard.releaseKey(Keyboard.Keys.KEY_8)

        assertEquals(Keyboard.Keys.KEY_8, keyboard.lastKeyReleased)

        keyboard.clearLastKeyReleased()

        assertEquals(Keyboard.Keys.NONE, keyboard.lastKeyReleased)
    }
}
