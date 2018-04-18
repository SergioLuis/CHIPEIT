package es.chipeit.lib.core.log

import es.chipeit.lib.core.Keyboard
import es.chipeit.lib.io.IUserKeyboard

internal class LoggedKeyboard(private val keyboard: Keyboard) : IUserKeyboard {
    override fun pressKey(key: IUserKeyboard.Keys) {
        // TODO add log
        keyboard.pressKey(key)
    }

    override fun releaseKey(key: IUserKeyboard.Keys) {
        // TODO add log
        keyboard.releaseKey(key)
    }
}
