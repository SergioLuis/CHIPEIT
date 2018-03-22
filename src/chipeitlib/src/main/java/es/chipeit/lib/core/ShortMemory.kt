package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory

internal class ShortMemory(override val size: Int): IMemory<Short> {
    private var memory: ShortArray = ShortArray(size)

    override fun get(index: Int): Short {
        return memory[index]
    }

    override fun set(index: Int, value: Short) {
        memory[index] = value
    }
}