package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IGraphicMemory
import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.extensions.nonNegativeRem

internal class WrappedGraphicMemory(override val width: Int, private val memory: IMemory<Byte>) : IGraphicMemory {
    init {
        if (width <= 0)
            throw IllegalArgumentException("width must be positive")

        if (memory.size % width != 0)
            throw IllegalArgumentException("memory size must be divisible in rows")
    }

    override val height: Int = memory.size / width

    private fun getIndex(x: Int, y: Int): Int {
        val column = x.nonNegativeRem(width)
        val row = y.nonNegativeRem(height)

        return width * row + column
    }

    override operator fun get(x: Int, y: Int): Byte {
        return memory[getIndex(x, y)]
    }

    override operator fun set(x: Int, y: Int, value: Byte) {
        memory[getIndex(x, y)] = value
    }

    override fun fill(element: Byte, fromIndex: Int, toIndex: Int) {
        memory.fill(element, fromIndex, toIndex)
    }
}
