package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.IClockDivider

internal abstract class ClockDivider(
        protected val clock: IClock,
        msPerStep: Long
) : IClockDivider {
    override var msPerStep = msPerStep
        set(value) {
            if (value <= 0)
                throw IllegalArgumentException("Non-positive ms per step not allowed ($value)")
            field = value
        }
    protected var lastTime = clock.getMs()

    override fun msLeft(): Long = (lastTime + msPerStep) - clock.getMs()
    override fun trigger() {
        lastTime += msPerStep
    }
}
