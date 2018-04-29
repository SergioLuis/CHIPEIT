package es.chipeit.lib.interfaces

import es.chipeit.lib.io.IGraphicMemory

internal interface ICoreGraphicMemory : IGraphicMemory {
    fun drawRow(x: Int, y: Int, bitline: Byte): Boolean
    fun clear()
}
