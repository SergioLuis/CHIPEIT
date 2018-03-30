package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.IClockDivider
import es.chipeit.lib.interfaces.IClockObserver

internal class ClockDivider : IClockDivider {
    private val clock: IClock
    private var lastTime: Long
    val observers = ArrayList<IClockObserver>()

    override var msPerStep: Long
        set(value) {
            if (value <= 0)
                throw IllegalArgumentException("Non-positive ms per step not allowed ($value)")
            field = value
        }

    constructor(clock: IClock, msPerStep: Long) {
        this.clock = clock
        this.msPerStep = msPerStep
        lastTime = 0
    }

    override fun reset() {
        lastTime = clock.ms
    }

    override val msLeft: Long
        get() = (lastTime + msPerStep) - clock.ms

    override fun trigger() {
        if (msLeft > 0L)
            return

        for (o in observers)
            o.onClockTick()

        lastTime += msPerStep
    }
}
