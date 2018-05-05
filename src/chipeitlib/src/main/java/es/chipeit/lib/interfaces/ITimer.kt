package es.chipeit.lib.interfaces

internal interface ITimer : IClockObserver {
    var t: Byte

    fun isActive(): Boolean
}
