package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.ISwitchObserver
import es.chipeit.lib.extensions.*

internal class Timer(observer: ISwitchObserver = NullSwitchObserver()) : ITimer {
    private val observer: ISwitchObserver = observer
    private var t: Byte = 0x0

    override fun isActive(): Boolean = t != Byte.ZERO

    override fun setRegister(value: Byte) {
        t = value
        if (isActive()) {
            observer.onEnable()
        }
    }

    override fun decrementRegister() {
        if (isActive()) {
            t--
            return
        }
        observer.onDisable()
    }
}
