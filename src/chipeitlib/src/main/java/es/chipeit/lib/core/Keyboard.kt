package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ICoreKeyboard
import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.IUserKeyboard

internal class Keyboard(override val soundTimer: ITimer) : IUserKeyboard, ICoreKeyboard {
    private var pressedKeys: Int = 0

    private var _capturedKeyRelease: IUserKeyboard.Keys = IUserKeyboard.Keys.NONE
    override val capturedKeyRelease: IUserKeyboard.Keys
        get() = _capturedKeyRelease

    private var _isCapturingNextKeyRelease: Boolean = false
    override val isCapturingNextKeyRelease: Boolean
        get() = _isCapturingNextKeyRelease

    override fun captureNextKeyRelease() {
        _isCapturingNextKeyRelease = true

        if (pressedKeys != 0)
            soundTimer.setRegister(4)
    }

    override fun clearLastCapturedKeyRelease() {
        _capturedKeyRelease = IUserKeyboard.Keys.NONE
    }

    override fun updateSoundTimerCounterIfNeeded() {
        if (isCapturingNextKeyRelease && pressedKeys != 0)
            soundTimer.setRegister(4)
    }

    override fun isPressed(key: IUserKeyboard.Keys): Boolean {
        return (pressedKeys and key.data.flags) != 0
    }

    override fun pressKey(key: IUserKeyboard.Keys) {
        pressedKeys = pressedKeys or key.data.flags
        soundTimer.setRegister(4)
    }

    override fun releaseKey(key: IUserKeyboard.Keys) {
        if (_isCapturingNextKeyRelease && isPressed(key))
        {
            _capturedKeyRelease = key
            _isCapturingNextKeyRelease = false
        }

        pressedKeys = pressedKeys and key.data.flags.inv()
    }
}
