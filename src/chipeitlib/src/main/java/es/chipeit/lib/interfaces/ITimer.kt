package es.chipeit.lib.interfaces

internal interface ITimer : IClockObserver {
    fun isActive(): Boolean
    fun setRegister(value: Byte)
}
