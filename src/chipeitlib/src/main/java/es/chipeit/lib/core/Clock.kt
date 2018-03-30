package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClock

internal class Clock : IClock {
    private var lastMs: Long = 0

    override var ms: Long = 0
    get() = lastMs

    override fun update() {
      lastMs = System.currentTimeMillis()
    }
}
