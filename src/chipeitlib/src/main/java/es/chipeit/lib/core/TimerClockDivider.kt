package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.ITimer

internal class TimerClockDivider(
        clock: IClock,
        msPerStep: Long
) : ClockDivider(clock, msPerStep) {
    val timers = ArrayList<ITimer>()

    override fun trigger() {
        /*
        Trigger returns if it’s not ready as a (optional) mean
        to implement busy waiting.
        */
        if (msLeft() > 0L)
            return

        for (t in timers)
            t.decrementRegister()

        super.trigger()
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
