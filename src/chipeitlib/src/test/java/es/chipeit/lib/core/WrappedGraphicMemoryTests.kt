package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IGraphicMemory
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class WrappedGraphicMemoryTests {
    companion object {
        internal fun checkCleanMemory(graphicMemory: IGraphicMemory) {
            for (i in 0..graphicMemory.width - 1)
                for (j in 0..graphicMemory.height - 1)
                    assertEquals(0x00, graphicMemory[i, j])
        }

        internal fun checkFootprint(graphicMemory: IGraphicMemory, x: Int, y: Int, value: Byte) {
            for (i in 0..graphicMemory.width - 1)
                for (j in 0..graphicMemory.height - 1)
                    if ((i == x) and (j == y))
                        assertEquals(value, graphicMemory[i, j])
                    else
                        assertEquals(0x00, graphicMemory[i, j])
        }
    }

    @Test
    fun graphicMemoryInvalidWidthTest() {
        assertFailsWith<IllegalArgumentException> {
            WrappedGraphicMemory(-1, ByteMemory(ByteArray(1)));
        }

        assertFailsWith<IllegalArgumentException> {
            WrappedGraphicMemory(0, ByteMemory(ByteArray(1)));
        }
    }

    @Test
    fun graphicMemoryInvalidMemorySizeTest() {
        assertFailsWith<IllegalArgumentException> {
            WrappedGraphicMemory(2, ByteMemory(ByteArray(1)));
        }

        assertFailsWith<IllegalArgumentException> {
            WrappedGraphicMemory(2, ByteMemory(ByteArray(3)));
        }

        // assertNotFails
        WrappedGraphicMemory(2, ByteMemory(ByteArray(2)));
    }

    @Test
    fun graphicMemory() {
        val ones: Byte = 0xFF.toByte()
        val WIDTH = 2
        val HEIGHT = 2
        val graphicMemory = WrappedGraphicMemory(2, ByteMemory(ByteArray(WIDTH * HEIGHT)));

        checkCleanMemory(graphicMemory)

        graphicMemory[0, 0] = ones;
        checkFootprint(graphicMemory, 0, 0, ones)

        graphicMemory[WIDTH, HEIGHT] = 0x00
        checkCleanMemory(graphicMemory)

        graphicMemory[1, 0] = ones;
        checkFootprint(graphicMemory, 1, 0, ones)

        graphicMemory[- WIDTH + 1, - HEIGHT] = 0x00;
        checkCleanMemory(graphicMemory)

        graphicMemory[0, 1] = ones;
        checkFootprint(graphicMemory, 0, 1, ones)

        graphicMemory[- WIDTH, - HEIGHT + 1] = 0x00;
        checkCleanMemory(graphicMemory)
    }
}
