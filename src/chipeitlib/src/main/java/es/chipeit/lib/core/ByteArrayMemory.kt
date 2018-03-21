package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory

internal class ByteArrayMemory(size: Int): IMemory<Short> {
    private var memory: ShortArray = ShortArray(size)

    override fun get(index: Int): Short {
        // TODO add log
        return memory[index]
    }

    override fun set(index: Int, value: Short) {
        // TODO add log
        memory[index] = value
    }

}