package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory

internal class PaddedMemory<T>(
        private val memory: IMemory<T>,
        private val padding: Int
) : IMemory<T> {
    override val size: Int
        get() = memory.size + padding

    override fun get(index: Int): T {
        return memory[index - padding]
    }

    override fun set(index: Int, value: T) {
        memory[index - padding] = value
    }
}
