package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IGraphicMemory

internal class GraphicMemory(memory: ByteArray) : IGraphicMemory, ByteMemory(memory) {
    override fun fill(element: Byte, fromIndex: Int, toIndex: Int) {
        memory.fill(element, fromIndex, toIndex)
    }
}
