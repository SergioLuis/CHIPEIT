package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock

internal class Clock : IClock {
    override fun getMs(): Long = System.currentTimeMillis()
}
