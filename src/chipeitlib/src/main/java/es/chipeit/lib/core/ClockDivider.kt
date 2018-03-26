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
        this.lastTime = this.clock.getMs()
    }

    override fun msLeft(): Long = (lastTime + msPerStep) - clock.getMs()

    override fun trigger() {
        if (msLeft() > 0L)
            return

        for (o in observers)
            o.onClockTick()

        lastTime += msPerStep
    }
}

/*

TODO: Check if this way of reasoning works! Consider coroutines

Clock Thread {

    // Thread actual work
    fun doWork() {
        while (true) {
            // This IClockDivider handles countdown timers
            delaysClockDivider.trigger()

            // This IClockDivider triggers CPU cycle execution
            instructionsClockDivider.trigger()

            Thread.Sleep(
                Math.max(0,
                    Math.min(
                        delaysClockDivider.msLeft(),
                        instructionsClockDivider.msLeft()
                    )
                )
            )
        }
    }

    IClockDivider delaysClockDivider          // 60Hz
    IClockDivider instructionsClockDivider    // 500Hz
}

 */
