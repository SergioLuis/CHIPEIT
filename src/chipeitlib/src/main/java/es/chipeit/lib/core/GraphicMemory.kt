package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ICoreGraphicMemory
import es.chipeit.lib.extensions.nonNegativeRem

internal class GraphicMemory(private val memory: Array<Array<Boolean>>) : ICoreGraphicMemory {
    override val width: Int
        get() = memory[0].size

    override val height: Int
        get() = memory.size

    override fun get(x: Int, y: Int): Boolean {
        return memory[y][x]
    }

    private operator fun set(x: Int, y: Int, value: Boolean): Boolean {
        val column = x.nonNegativeRem(width)
        val row = y.nonNegativeRem(height)

        val pixelState = memory[row][column]
        memory[row][column] = memory[row][column] xor value

        // Check if the pixel was cleared.
        return pixelState && !memory[row][column]
    }

    override fun drawLine(x: Int, y: Int, value: Byte): Boolean {
        @SuppressWarnings
        val value: Int = value.toInt() and 0xFF

        var pixelCleared = false
        for (i in 7 downTo 0)
            pixelCleared = pixelCleared or set(x, y, (value shr i and 0x1) == 0x1)

        return pixelCleared
    }

    override fun clear() {
        for (column in memory)
            column.fill(false)
    }
}
