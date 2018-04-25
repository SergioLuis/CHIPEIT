package es.chipeit.lib.interfaces

internal interface IMemory<T> {
    val size: Int
    operator fun get(index: Int): T
    operator fun set(index: Int, value: T)
    fun fill(element: T, fromIndex: Int = 0, toIndex: Int = size)
}
