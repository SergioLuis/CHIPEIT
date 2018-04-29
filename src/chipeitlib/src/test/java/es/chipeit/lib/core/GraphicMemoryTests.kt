package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class GraphicMemoryTests {
    companion object {
        private fun compare(l: Array<Array<Boolean>>, r: Array<Array<Boolean>>): Boolean {
            if (l.size != r.size)
                throw IllegalArgumentException("Matrix sizes must match")

            for (j in 0 until l.size) {
                if (l[j].size != r[j].size)
                    throw IllegalArgumentException("Matrix sizes must match")

                for (i in 0 until l[j].size)
                    if (l[j][i] != r[j][i])
                        return false
            }

            return true
        }
    }

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

        graphicMemory.drawRow(0, 0, 0x00)
        assertTrue { compare(rawMatrix, originalMatrix) }

        graphicMemory.drawRow(0, 0, 0xFF.toByte())
        assertTrue { compare(rawMatrix, negativeMatrix) }

        graphicMemory.drawRow(0, 0, 0xFF.toByte())
        assertTrue { compare(rawMatrix, originalMatrix) }
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

        graphicMemory.drawRow(0, 0, 0xF0.toByte())
        assertTrue { compare(rawMatrix, drawedAtOrigin) }
        graphicMemory.clear()

        graphicMemory.drawRow(-2, 0, 0xF0.toByte())
        assertTrue { compare(rawMatrix, leftShifted) }
        graphicMemory.clear()

        graphicMemory.drawRow(0, -1, 0xF0.toByte())
        assertTrue { compare(rawMatrix, upShifted) }
        graphicMemory.clear()
    }
}
