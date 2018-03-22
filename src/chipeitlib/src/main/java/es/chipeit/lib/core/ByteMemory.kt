package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory

internal class ByteMemory(override val size: Int): IMemory<Byte> {
    private var memory: ByteArray = ByteArray(size)

    override fun get(index: Int): Byte {
        return memory[index]
    }

    override fun set(index: Int, value: Byte) {
        memory[index] = value
    }
}