package es.chipeit.lib.core.log

import es.chipeit.lib.interfaces.IMemory

internal class LoggedMemory<T>(
        val identifier: String,
        private val memory: IMemory<T>
) : IMemory<T> {
    override val size: Int
        get() {
            // TODO add log
            return memory.size
        }

    override fun get(index: Int): T {
        // TODO add log
        return memory[index]
    }

    override fun set(index: Int, value: T) {
        // TODO add log
        memory[index] = value
    }

    override fun fill(element: T, fromIndex: Int, toIndex: Int) {
        // TODO add log
        memory.fill(element, fromIndex, toIndex)
    }
}
