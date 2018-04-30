package es.chipeit.lib.interfaces

import es.chipeit.lib.io.IUserKeyboard

internal interface ICoreKeyboard {
    val capturedKeyRelease: IUserKeyboard.Keys
    val isCapturingNextKeyRelease: Boolean
    val soundTimer: ITimer

    fun captureNextKeyRelease()
    fun clearLastCapturedKeyRelease()
    fun updateSoundTimerCounterIfNeeded()
    fun isPressed(key: IUserKeyboard.Keys) : Boolean
}
