package es.chipeit.console

import es.chipeit.lib.emulator.ISleeper

class DummySleeper : ISleeper {
    override fun sleep(milliseconds: Long) {
        Thread.sleep(milliseconds)
    }
}
