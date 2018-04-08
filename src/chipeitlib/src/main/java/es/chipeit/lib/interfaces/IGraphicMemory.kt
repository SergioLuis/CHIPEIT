package es.chipeit.lib.interfaces

internal interface IGraphicMemory : IMemory<Byte> {
    fun fill(element: Byte, fromIndex: Int = 0, toIndex: Int = size)
}
