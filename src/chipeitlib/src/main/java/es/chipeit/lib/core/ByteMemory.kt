package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory

internal class ByteMemory(size: Int): IMemory<Byte> {
    private var memory: ByteArray = ByteArray(size)

    override fun get(index: Int): Byte {
        // TODO add log
        return memory[index]
    }

    override fun set(index: Int, value: Byte) {
        // TODO add log
        memory[index] = value
    }
}