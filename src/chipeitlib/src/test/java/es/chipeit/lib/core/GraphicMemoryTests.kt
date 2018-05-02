package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GraphicMemoryTests {
    @Test
    fun illegalSizesTest() {
        var halfBakedMatrix = emptyArray<Array<Boolean>>()
        assertFailsWith<IllegalArgumentException> { GraphicMemory(halfBakedMatrix) }

        halfBakedMatrix = arrayOf(emptyArray<Boolean>())
        assertFailsWith<IllegalArgumentException> { GraphicMemory(halfBakedMatrix) }

        halfBakedMatrix = arrayOf(arrayOf(false))
        assertFailsWith<IllegalArgumentException> { GraphicMemory(halfBakedMatrix) }

        halfBakedMatrix = arrayOf(Array(8) { false })
        //assertNotFails
        GraphicMemory(halfBakedMatrix)
    }

    @Test
    fun clearTest() {
        val nonSquareMatrix = Array(32) { Array(64) { true } }
        val graphicMemory = GraphicMemory(nonSquareMatrix)

        graphicMemory.clear()

        for (j in 0 until graphicMemory.height)
            for (i in 0 until graphicMemory.width)
                assertEquals(false, graphicMemory[i, j])
    }

    @Test
    fun xorTest() {
        val rawMatrix = arrayOf(
                arrayOf(true, true, true, true,
                        false, false, false, false)
        )
        val graphicMemory = GraphicMemory(rawMatrix)

        val originalMatrix = arrayOf(
                arrayOf(true, true, true, true,
                        false, false, false, false)
        )
        val negativeMatrix = arrayOf(
                arrayOf(false, false, false, false,
                        true, true, true, true)
        )

        graphicMemory.drawLine(0, 0, 0x00)
        assertTrue { rawMatrix contentDeepEquals originalMatrix }

        graphicMemory.drawLine(0, 0, 0xFF.toByte())
        assertTrue { rawMatrix contentDeepEquals negativeMatrix }

        graphicMemory.drawLine(0, 0, 0xFF.toByte())
        assertTrue { rawMatrix contentDeepEquals originalMatrix }
    }

    @Test
    fun wrappingTest() {
        val rawMatrix = Array(2) { Array(8) { false } }

        val drawedAtOrigin = arrayOf(
                arrayOf(true, true, true, true,
                        false, false, false, false),
                arrayOf(false, false, false, false,
                        false, false, false, false)
        )

        val leftShifted = arrayOf(
                arrayOf(true, true, false, false,
                        false, false, true, true),
                arrayOf(false, false, false, false,
                        false, false, false, false)
        )

        val upShifted = arrayOf(
                arrayOf(false, false, false, false,
                        false, false, false, false),
                arrayOf(true, true, true, true,
                        false, false, false, false)
        )

        val graphicMemory = GraphicMemory(rawMatrix)

        graphicMemory.drawLine(0, 0, 0xF0.toByte())
        assertTrue { rawMatrix contentDeepEquals drawedAtOrigin }
        graphicMemory.clear()

        graphicMemory.drawLine(-2, 0, 0xF0.toByte())
        assertTrue { rawMatrix contentDeepEquals leftShifted }
        graphicMemory.clear()

        graphicMemory.drawLine(0, -1, 0xF0.toByte())
        assertTrue { rawMatrix contentDeepEquals upShifted }
        graphicMemory.clear()
    }
}
