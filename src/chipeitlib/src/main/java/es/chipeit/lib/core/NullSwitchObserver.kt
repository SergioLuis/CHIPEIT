package es.chipeit.lib.core

import es.chipeit.lib.io.ISwitchObserver

internal class NullSwitchObserver : ISwitchObserver {
    override fun onEnable() {}
    override fun onDisable() {}
}
