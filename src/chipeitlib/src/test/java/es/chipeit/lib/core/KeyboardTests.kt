package es.chipeit.lib.core

import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import es.chipeit.lib.io.IUserKeyboard
import es.chipeit.lib.interfaces.ITimer

class KeyboardTests {
    @Test
    fun keyDownAndUpTest() {
        val soundTimerMock = Mockito.mock(ITimer::class.java)
        val keyboard = Keyboard(soundTimerMock)

        keyboard.pressKey(IUserKeyboard.Keys.KEY_0)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_0))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_1)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_1))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_2)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_2))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_3)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_3))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_4)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_4))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_5)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_5))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_6)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_6))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_7)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_7))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_8)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_8))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_9)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_9))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_A)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_A))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_B)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_B))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_C)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_C))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_D)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_D))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_E)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_E))

        keyboard.pressKey(IUserKeyboard.Keys.KEY_F)
        assertTrue(keyboard.isPressed(IUserKeyboard.Keys.KEY_F))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_0)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_0))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_1)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_1))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_2)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_2))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_3)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_3))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_4)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_4))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_5)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_5))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_6)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_6))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_7)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_7))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_8)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_8))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_9)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_9))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_A)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_A))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_B)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_B))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_C)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_C))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_D)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_D))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_E)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_E))

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_F)
        assertFalse(keyboard.isPressed(IUserKeyboard.Keys.KEY_F))
    }

    @Test
    fun lastKeyReleaseCaptureTest() {
        val soundTimerMock = Mockito.mock(ITimer::class.java)
        val keyboard = Keyboard(soundTimerMock)

        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)

        keyboard.pressKey(IUserKeyboard.Keys.KEY_8)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)

        keyboard.captureNextKeyRelease()
        assertTrue(keyboard.isCapturingNextKeyRelease)

        // A Key is not considered as released if it was not pressed before
        keyboard.releaseKey(IUserKeyboard.Keys.KEY_0)
        assertTrue(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.NONE, keyboard.capturedKeyRelease)

        keyboard.releaseKey(IUserKeyboard.Keys.KEY_8)
        assertFalse(keyboard.isCapturingNextKeyRelease)
        assertEquals(IUserKeyboard.Keys.KEY_8, keyboard.capturedKeyRelease)
    }
}
