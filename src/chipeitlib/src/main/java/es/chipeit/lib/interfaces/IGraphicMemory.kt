package es.chipeit.lib.interfaces

internal interface IGraphicMemory {
    val width: Int
    val height: Int

    operator fun get(x: Int, y: Int): Byte
    operator fun set(x: Int, y: Int, value: Byte)
    fun fill(element: Byte, fromIndex: Int = 0, toIndex: Int = (width / 8) * height)
}
