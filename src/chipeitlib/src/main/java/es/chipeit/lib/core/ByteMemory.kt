package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory

internal open class ByteMemory(protected val memory: ByteArray): IMemory<Byte> {
    override val size: Int
        get() = memory.size

    override fun get(index: Int): Byte {
        return memory[index]
    }

    override fun set(index: Int, value: Byte) {
        memory[index] = value
    }
}
