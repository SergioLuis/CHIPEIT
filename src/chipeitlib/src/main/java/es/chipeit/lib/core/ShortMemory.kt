package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory

internal class ShortMemory(private val memory: ShortArray): IMemory<Short> {
    override val size: Int
        get() = memory.size

    override fun get(index: Int): Short {
        return memory[index]
    }

    override fun set(index: Int, value: Short) {
        memory[index] = value
    }

    override fun fill(element: Short, fromIndex: Int, toIndex: Int) {
        memory.fill(element, fromIndex, toIndex)
    }
}
