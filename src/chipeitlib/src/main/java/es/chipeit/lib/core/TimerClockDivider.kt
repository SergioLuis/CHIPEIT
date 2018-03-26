package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.ITimer

internal class TimerClockDivider(clock: IClock, msPerStep: Long) : ClockDivider(clock, msPerStep) {
    val timers: ArrayList<ITimer> = ArrayList<ITimer>()

    override fun trigger() {
        /* FIXME:
            Should block and be on a thread/coroutine?
            Or should be handled by the caller?
        */
        if (!isReady())
            return

        for (t in timers)
            t.decrementRegister()

        super.trigger()
    }
}
