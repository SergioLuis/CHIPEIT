package es.chipeit.lib.interfaces

import es.chipeit.lib.io.IGraphicMemory

internal interface ICoreGraphicMemory : IGraphicMemory {
    fun drawSpriteRow(x: Int, y: Int, spriteRow: Byte): Boolean
    fun clear()
}
