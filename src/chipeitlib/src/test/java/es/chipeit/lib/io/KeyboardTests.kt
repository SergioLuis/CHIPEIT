package es.chipeit.lib.io

import org.junit.Test
import kotlin.test.assertEquals

class KeyboardTests {
    @Test
    fun keyDownAndUpTest() {
        val keyboard = Keyboard()

        assertEquals(0, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_0)
        assertEquals(0x0001, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_1)
        assertEquals(0x0003, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_2)
        assertEquals(0x0007, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_3)
        assertEquals(0x000F, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_4)
        assertEquals(0x001F, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_5)
        assertEquals(0x003F, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_6)
        assertEquals(0x007F, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_7)
        assertEquals(0x00FF, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_8)
        assertEquals(0x01FF, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_9)
        assertEquals(0x03FF, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_A)
        assertEquals(0x07FF, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_B)
        assertEquals(0x0FFF, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_C)
        assertEquals(0x1FFF, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_D)
        assertEquals(0x3FFF, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_E)
        assertEquals(0x7FFF, keyboard.keysDown)

        keyboard.keyDown(Keyboard.Keys.KEY_F)
        assertEquals(0xFFFF, keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_0)
        assertEquals(0xFFFF and 0x0001.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_1)
        assertEquals(0xFFFF and 0x0003.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_2)
        assertEquals(0xFFFF and 0x0007.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_3)
        assertEquals(0xFFFF and 0x000F.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_4)
        assertEquals(0xFFFF and 0x001F.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_5)
        assertEquals(0xFFFF and 0x003F.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_6)
        assertEquals(0xFFFF and 0x007F.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_7)
        assertEquals(0xFFFF and 0x00FF.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_8)
        assertEquals(0xFFFF and 0x01FF.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_9)
        assertEquals(0xFFFF and 0x03FF.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_A)
        assertEquals(0xFFFF and 0x07FF.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_B)
        assertEquals(0xFFFF and 0x0FFF.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_C)
        assertEquals(0xFFFF and 0x1FFF.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_D)
        assertEquals(0xFFFF and 0x3FFF.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_E)
        assertEquals(0xFFFF and 0x7FFF.inv(), keyboard.keysDown)

        keyboard.keyUp(Keyboard.Keys.KEY_F)
        assertEquals(0xFFFF and 0xFFFF.inv(), keyboard.keysDown)
    }
}
