package es.chipeit.lib.interfaces

internal interface ITimer {
    fun isActive(): Boolean

    fun setRegister(value: Byte)
    fun decrementRegister()
}