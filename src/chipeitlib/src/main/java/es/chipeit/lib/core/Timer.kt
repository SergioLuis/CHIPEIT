package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.ISwitchObserver

internal class Timer(private val observer: ISwitchObserver = NullSwitchObserver()) : ITimer {
    override var t: Byte = 0x0
        set(value) {
            val wasActive = isActive()
            field = value

            if (isActive() && !wasActive)
                observer.onEnable()
            else if(!isActive() && wasActive)
                observer.onDisable()
        }

    override fun isActive(): Boolean = t != ZERO

    override fun onClockTick() {
        if (!isActive())
            return

        t--
    }

    companion object {
        const val ZERO: Byte = 0x0
    }
}
