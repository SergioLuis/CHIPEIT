package es.chipeit.lib.core.log

import es.chipeit.lib.interfaces.IGraphicMemory

internal class LoggedGraphicMemory(
        val identifier: String,
        private val memory: IGraphicMemory
) : IGraphicMemory {
    override val size: Int
        get() {
            // TODO add log
            return memory.size
        }

    override fun get(index: Int): Byte {
        // TODO add log
        return memory[index]
    }

    override fun set(index: Int, value: Byte) {
        // TODO add log
        memory[index] = value
    }

    override fun fill(element: Byte, fromIndex: Int, toIndex: Int) {
        memory.fill(element, fromIndex, toIndex)
    }
}
