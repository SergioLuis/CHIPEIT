package es.chipeit.console

import es.chipeit.lib.emulator.ISleeper

class DummySleeper : ISleeper {
    override fun sleep(ms: Long) {
        Thread.sleep(ms)
    }
}
