package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ICoreGraphicMemory

internal class GraphicMemory(private val memory: Array<Array<Boolean>>) : ICoreGraphicMemory {
    override val width: Int
        get() = memory[0].size

    override val height: Int
        get() = memory.size

    init {
        if (height <= 0 || width <= 0)
            throw IllegalArgumentException("Matrix dimensions must be greater than zero")

        if (width < 8)
            throw IllegalArgumentException("Width must be greater than eight")
    }

    override fun get(x: Int, y: Int): Boolean {
        return memory[y][x]
    }

    override fun drawLine(x: Int, y: Int, line: Byte): Boolean {
        val row = memory[getCircularIndex(y, height)]
        val pixels: Int = line.toInt() and 0xFF

        var pixelCleared = false

        for (i in 0..7) {
            val column = getCircularIndex(x + i, width)
            val previousState = row[column]

            row[column] = previousState xor ((pixels shr (7 - i) and 0x1) == 0x1)
            pixelCleared = pixelCleared || (previousState && !row[column])
        }

        return pixelCleared
    }

    override fun clear() {
        for (column in memory)
            column.fill(false)
    }

    companion object {
        private fun getCircularIndex(index: Int, size: Int): Int{
            val rem = index % size
            return if (rem < 0) rem + size else rem
        }
    }
}
