package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ICoreGraphicMemory
import es.chipeit.lib.extensions.nonNegativeRem

internal class GraphicMemory(private val memory: Array<Array<Boolean>>) : ICoreGraphicMemory {
    override val width: Int
        get() = memory[0].size

    override val height: Int
        get() = memory.size

    init {
        if(height <= 0 || width <= 0)
            throw IllegalArgumentException("matrix dimensions must be greater than zero")

        if(width < 8)
            throw IllegalArgumentException("width must be greater than eight")
    }

    override fun get(x: Int, y: Int): Boolean {
        return memory[y][x]
    }

    override fun drawRow(x: Int, y: Int, bitline: Byte): Boolean {
        val leftmostColumn = x.nonNegativeRem(width)
        val row = y.nonNegativeRem(height)
        @SuppressWarnings val bitline: Int = bitline.toInt() and 0xFF

        var pixelCleared = false

        for (i in 0..7) {
            val column = leftmostColumn + i

            val previousState = memory[row][column]
            memory[row][column] = previousState xor ((bitline shr (7 - i) and 0x1) == 0x1)

            if (previousState && !memory[row][column])
                pixelCleared = true
        }

        return pixelCleared
    }

    override fun clear() {
        for (column in memory)
            column.fill(false)
    }
}
