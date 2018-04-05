package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import es.chipeit.lib.io.IKeyboard

class KeyboardTests {
    @Test
    fun keyDownAndUpTest() {
        val keyboard = Keyboard()

        keyboard.pressKey(IKeyboard.Keys.KEY_0)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_0))

        keyboard.pressKey(IKeyboard.Keys.KEY_1)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_1))

        keyboard.pressKey(IKeyboard.Keys.KEY_2)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_2))

        keyboard.pressKey(IKeyboard.Keys.KEY_3)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_3))

        keyboard.pressKey(IKeyboard.Keys.KEY_4)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_4))

        keyboard.pressKey(IKeyboard.Keys.KEY_5)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_5))

        keyboard.pressKey(IKeyboard.Keys.KEY_6)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_6))

        keyboard.pressKey(IKeyboard.Keys.KEY_7)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_7))

        keyboard.pressKey(IKeyboard.Keys.KEY_8)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_8))

        keyboard.pressKey(IKeyboard.Keys.KEY_9)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_9))

        keyboard.pressKey(IKeyboard.Keys.KEY_A)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_A))

        keyboard.pressKey(IKeyboard.Keys.KEY_B)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_B))

        keyboard.pressKey(IKeyboard.Keys.KEY_C)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_C))

        keyboard.pressKey(IKeyboard.Keys.KEY_D)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_D))

        keyboard.pressKey(IKeyboard.Keys.KEY_E)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_E))

        keyboard.pressKey(IKeyboard.Keys.KEY_F)
        assertTrue(keyboard.isPressed(IKeyboard.Keys.KEY_F))

        keyboard.releaseKey(IKeyboard.Keys.KEY_0)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_0))

        keyboard.releaseKey(IKeyboard.Keys.KEY_1)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_1))

        keyboard.releaseKey(IKeyboard.Keys.KEY_2)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_2))

        keyboard.releaseKey(IKeyboard.Keys.KEY_3)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_3))

        keyboard.releaseKey(IKeyboard.Keys.KEY_4)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_4))

        keyboard.releaseKey(IKeyboard.Keys.KEY_5)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_5))

        keyboard.releaseKey(IKeyboard.Keys.KEY_6)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_6))

        keyboard.releaseKey(IKeyboard.Keys.KEY_7)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_7))

        keyboard.releaseKey(IKeyboard.Keys.KEY_8)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_8))

        keyboard.releaseKey(IKeyboard.Keys.KEY_9)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_9))

        keyboard.releaseKey(IKeyboard.Keys.KEY_A)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_A))

        keyboard.releaseKey(IKeyboard.Keys.KEY_B)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_B))

        keyboard.releaseKey(IKeyboard.Keys.KEY_C)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_C))

        keyboard.releaseKey(IKeyboard.Keys.KEY_D)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_D))

        keyboard.releaseKey(IKeyboard.Keys.KEY_E)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_E))

        keyboard.releaseKey(IKeyboard.Keys.KEY_F)
        assertFalse(keyboard.isPressed(IKeyboard.Keys.KEY_F))
    }

    @Test
    fun lastKeyReleaseCaptureTest() {
        val keyboard = Keyboard()

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IKeyboard.Keys.NONE, keyboard.capturedKeyRelease)

        keyboard.pressKey(IKeyboard.Keys.KEY_8)
        assertEquals(IKeyboard.Keys.NONE, keyboard.capturedKeyRelease)

        keyboard.captureNextKeyRelease()
        assertTrue(keyboard.isCapturingNextKeyRelease)

        // A Key is not considered as released if it was not pressed before
        keyboard.releaseKey(IKeyboard.Keys.KEY_0)
        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IKeyboard.Keys.NONE, keyboard.capturedKeyRelease)

        keyboard.releaseKey(IKeyboard.Keys.KEY_8)
        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IKeyboard.Keys.KEY_8, keyboard.capturedKeyRelease)
    }
}
