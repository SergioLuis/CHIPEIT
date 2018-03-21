package es.chipeit.lib.interfaces

interface IMemory<T> {
    operator fun get(index: Int): T
    operator fun set(index: Int, value: T)
}