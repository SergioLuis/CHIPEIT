package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory
import kotlin.experimental.xor

internal class XorMemory(private val memory: IMemory<Byte>) : IMemory<Byte> {
    override val size: Int
        get() = memory.size

    override fun get(index: Int): Byte {
        return memory[index]
    }

    override fun set(index: Int, value: Byte) {
        memory[index] = memory[index] xor value
    }

    override fun fill(element: Byte, fromIndex: Int, toIndex: Int) {
        memory.fill(element, fromIndex, toIndex)
    }
}
