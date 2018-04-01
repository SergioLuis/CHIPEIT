package es.chipeit.console

import es.chipeit.lib.io.ISwitchObserver

class DummySoundPlayer : ISwitchObserver {
    override fun onEnable() {
        println("onEnable!")
    }

    override fun onDisable() {
        println("onDisable!")
    }

}
