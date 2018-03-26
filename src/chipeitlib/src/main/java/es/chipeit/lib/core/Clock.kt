package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock

internal class Clock() : IClock {
    private var now: Long = 0

    override fun update(): IClock {
        now = System.currentTimeMillis()
        return this
    }

    override fun getMs(): Long = now
}
