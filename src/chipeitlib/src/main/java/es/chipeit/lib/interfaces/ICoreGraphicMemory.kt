package es.chipeit.lib.interfaces

import es.chipeit.lib.io.IGraphicMemory

internal interface ICoreGraphicMemory: IGraphicMemory {
    fun drawLine(x: Int, y: Int, value: Byte)
    fun clear()
}
