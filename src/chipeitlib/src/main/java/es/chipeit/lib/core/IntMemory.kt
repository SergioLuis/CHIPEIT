package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory

internal class IntMemory(private val memory: IntArray): IMemory<Int> {
    override val size: Int
        get() = memory.size

    override fun get(index: Int): Int {
        return memory[index]
    }

    override fun set(index: Int, value: Int) {
        memory[index] = value
    }

    override fun fill(element: Int, fromIndex: Int, toIndex: Int) {
        memory.fill(element, fromIndex, toIndex)
    }
}
