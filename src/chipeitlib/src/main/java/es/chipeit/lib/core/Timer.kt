package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.ISwitchObserver
import es.chipeit.lib.extensions.*

internal class Timer(observer: ISwitchObserver = NullSwitchObserver()) : ITimer {
    private val observer: ISwitchObserver = observer
    override var t: Byte = 0x0
        set(value: Byte) {
            val wasActive = isActive()
            field = value
            if (isActive() && !wasActive)
                observer.onEnable()
            else if(!isActive() && wasActive)
                observer.onDisable()
        }

    override fun isActive(): Boolean = t != Byte.ZERO

    override fun onClockTick() {
        if (!isActive())
            return

        t--
    }
}
