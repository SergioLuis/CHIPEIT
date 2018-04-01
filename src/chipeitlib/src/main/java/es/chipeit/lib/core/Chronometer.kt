package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.IChronometer

internal class Chronometer(
        private val clock: IClock
) : IChronometer {
    private var idleTime = 0L
    private var lastStop = 0L
    private var clockMs = 0L
    private var _isRunning = false

    override val ms: Long
        get() = clock.ms - idleTime

    override val isRunning: Boolean
        get() = _isRunning

    override fun start() {
        if (isRunning)
            return

        clock.update()
        idleTime += clock.ms - lastStop
        _isRunning = true
    }

    override fun stop() {
        if (!isRunning)
            return

        _isRunning = false
        lastStop = clock.ms
    }

    override fun update(): IClock {
        if (!isRunning)
            throw IllegalStateException("The chronometer was not started")

        clockMs = clock.update().ms
        return this
    }
}
