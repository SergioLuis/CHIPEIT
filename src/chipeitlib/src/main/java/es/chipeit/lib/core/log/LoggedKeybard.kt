package es.chipeit.lib.core.log

import es.chipeit.lib.io.IKeyboard

internal class LoggedKeybard(private val keyboard: IKeyboard) : IKeyboard {
    override fun pressKey(key: IKeyboard.Keys) {
        // TODO add log
        keyboard.pressKey(key)
    }

    override fun releaseKey(key: IKeyboard.Keys) {
        // TODO add log
        keyboard.releaseKey(key)
    }

}
