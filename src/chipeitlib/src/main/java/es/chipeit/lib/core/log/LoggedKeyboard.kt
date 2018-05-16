package es.chipeit.lib.core.log

import es.chipeit.lib.core.Keyboard
import es.chipeit.lib.interfaces.ICoreKeyboard
import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.IUserKeyboard

internal class LoggedKeyboard(private val keyboard: Keyboard) : IUserKeyboard, ICoreKeyboard {
    override val capturedKeyRelease: IUserKeyboard.Keys
        get() {
            // TODO add log
            return keyboard.capturedKeyRelease
        }

    override val isCapturingNextKeyRelease: Boolean
        get() {
            // TODO add log
            return keyboard.isCapturingNextKeyRelease
        }

    override val soundTimer: ITimer
        get() {
            // TODO add log
            return keyboard.soundTimer
        }

    override fun captureNextKeyRelease() {
        // TODO add log
        keyboard.captureNextKeyRelease()
    }

    override fun clearLastCapturedKeyRelease() {
        // TODO add log
        keyboard.clearLastCapturedKeyRelease()
    }

    override fun updateSoundTimerCounterIfNeeded() {
        // TODO add log
        keyboard.updateSoundTimerCounterIfNeeded()
    }

    override fun isPressed(key: IUserKeyboard.Keys): Boolean {
        // TODO add log
        return keyboard.isPressed(key)
    }

    override fun pressKey(key: IUserKeyboard.Keys) {
        // TODO add log
        keyboard.pressKey(key)
    }

    override fun releaseKey(key: IUserKeyboard.Keys) {
        // TODO add log
        keyboard.releaseKey(key)
    }
}
