package es.chipeit.lib.core

import es.chipeit.lib.io.IKeyboard

class Keyboard : IKeyboard {
    private var pressedKeys: Int = 0

    private var _capturedKeyRelease: IKeyboard.Keys = IKeyboard.Keys.NONE
    internal val capturedKeyRelease: IKeyboard.Keys
        get() = _capturedKeyRelease

    private var _isCapturingNextKeyRelease: Boolean = false
    internal val isCapturingNextKeyRelease: Boolean
        get() = _isCapturingNextKeyRelease

    internal fun captureNextKeyRelease() {
        _isCapturingNextKeyRelease = true
    }

    internal fun clearLastCapturedKeyRelease() {
        _capturedKeyRelease = IKeyboard.Keys.NONE
    }

    internal fun isPressed(key: IKeyboard.Keys): Boolean {
        return (pressedKeys and key.data.flags) != 0
    }

    override fun pressKey(key: IKeyboard.Keys) {
        pressedKeys = pressedKeys or key.data.flags
    }

    override fun releaseKey(key: IKeyboard.Keys) {
        if (_isCapturingNextKeyRelease && isPressed(key))
        {
            _capturedKeyRelease = key
            _isCapturingNextKeyRelease = false
        }

        pressedKeys = pressedKeys and key.data.flags.inv()
    }
}
