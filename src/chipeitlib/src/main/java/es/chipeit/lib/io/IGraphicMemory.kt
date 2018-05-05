package es.chipeit.lib.io

interface IGraphicMemory {
    val width: Int
    val height: Int

    operator fun get(x: Int, y: Int): Boolean
}
