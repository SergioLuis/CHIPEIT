package es.chipeit.lib.emulator

class Chipeit {
    @Volatile
    private var running: Boolean = false

    fun run() {
        running = true

        while (running) {
            // TODO: Trigger clock dividers.
            // TODO: Sleep (seriously).
        }
    }

    fun stop() {
        running = false;
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
