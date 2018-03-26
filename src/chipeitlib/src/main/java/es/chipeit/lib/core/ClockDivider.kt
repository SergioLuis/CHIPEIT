package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.IClockDivider

internal abstract class ClockDivider(val clock: IClock, msPerStep: Long) : IClockDivider {
    override var msPerStep = msPerStep
        set(value) {
            if (value < 0)
                throw IllegalArgumentException("Negative ms per step not allowed ($value)")
            field = value
        }
    protected var lastTime = clock.getMs()

    override fun msLeft(): Long = (lastTime + msPerStep) - clock.getMs()
    override fun trigger() {
        lastTime += msPerStep
    }
}
