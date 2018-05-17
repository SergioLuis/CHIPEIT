package es.chipeit.android.ui.emulator

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import es.chipeit.android.io.ResourceReader
import es.chipeit.lib.emulator.Chipeit
import es.chipeit.lib.emulator.ISleeper
import es.chipeit.lib.io.IGraphicMemory
import es.chipeit.lib.io.ISwitchObserver
import es.chipeit.lib.io.IUserKeyboard

class ChipeitEmulator(
        context: Context,
        romFileName: String,
        keyboard: EmulatorKeyboard,
        screen: EmulatorScreen,
        enableLoadStoreQuirk: Boolean,
        enableShiftQuirk: Boolean) {
    private val core = Chipeit(
            soundPlayer = EmulatorSoundPlayer(),
            romContent = loadRom(context, romFileName),
            cpuClockRate = 500,
            timersClockRate = 60,
            sleeper = EmulatorSleeper(),
            enableLoadStoreQuirk = enableLoadStoreQuirk,
            enableShiftQuirk = enableShiftQuirk
    )

    private val emulationThreadRunnable = EmulationThreadRunnable(core)
    private val renderThreadRunnable = RenderThreadRunnable(core, screen)

    init {
        keyboard.keyZero.tag = IUserKeyboard.Keys.KEY_0
        keyboard.keyOne.tag = IUserKeyboard.Keys.KEY_1
        keyboard.keyTwo.tag = IUserKeyboard.Keys.KEY_2
        keyboard.keyThree.tag = IUserKeyboard.Keys.KEY_3
        keyboard.keyFour.tag = IUserKeyboard.Keys.KEY_4
        keyboard.keyFive.tag = IUserKeyboard.Keys.KEY_5
        keyboard.keySix.tag = IUserKeyboard.Keys.KEY_6
        keyboard.keySeven.tag = IUserKeyboard.Keys.KEY_7
        keyboard.keyEight.tag = IUserKeyboard.Keys.KEY_8
        keyboard.keyNine.tag = IUserKeyboard.Keys.KEY_9
        keyboard.keyA.tag = IUserKeyboard.Keys.KEY_A
        keyboard.keyB.tag = IUserKeyboard.Keys.KEY_B
        keyboard.keyC.tag = IUserKeyboard.Keys.KEY_C
        keyboard.keyD.tag = IUserKeyboard.Keys.KEY_D
        keyboard.keyE.tag = IUserKeyboard.Keys.KEY_E
        keyboard.keyF.tag = IUserKeyboard.Keys.KEY_F

        val touchListener = KeyboardButtonOnTouchListener(core.UserKeyboard)
        for (key in keyboard.keys) {
            key.setOnTouchListener(touchListener)
        }
    }

    fun start() {
        val emulationThread = Thread(emulationThreadRunnable)
        val renderThread = Thread(renderThreadRunnable)

        renderThread.start()
        emulationThread.start()
    }

    fun stop() {
        emulationThreadRunnable.stop()
        renderThreadRunnable.stop()
    }

    @SuppressLint("ClickableViewAccessibility")
    private class KeyboardButtonOnTouchListener(val keyboard: IUserKeyboard) : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            return when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    val tag = v?.tag as? IUserKeyboard.Keys ?: return false
                    keyboard.pressKey(tag)
                    v.isPressed = true
                    true
                }

                MotionEvent.ACTION_UP -> {
                    val tag = v?.tag as? IUserKeyboard.Keys ?: return false
                    keyboard.releaseKey(tag)
                    v.isPressed = false
                    true
                }

                else -> false
            }
        }
    }

    private class EmulatorSoundPlayer : ISwitchObserver {
        override fun onEnable() {
            // TODO
        }

        override fun onDisable() {
            // TODO
        }
    }

    private class EmulatorSleeper : ISleeper {
        override fun sleep(ms: Long) {
            Thread.sleep(ms)
        }
    }

    private class EmulationThreadRunnable(private val core: Chipeit) : Runnable {
        fun stop() {
            core.stop()
        }

        override fun run() {
            core.run()
        }
    }

    private class RenderThreadRunnable(
            private val core: Chipeit,
            private val screen: EmulatorScreen) : Runnable {
        private var running = true
        val uiThread = UIThread(screen)

        fun stop() {
            running = false
        }

        override fun run() {
            running = true
            while (running) {
                uiThread.graphicMemory = core.GraphicMemory
                screen.post(uiThread)

                // FIXME
                Thread.sleep(16)
            }
        }

        private class UIThread(
                private val screen: EmulatorScreen) : Runnable {
            lateinit var graphicMemory: IGraphicMemory

            override fun run() {
                screen.redraw(graphicMemory)
            }
        }
    }

    companion object {
        fun loadRom(context: Context, romFileName: String): ByteArray {
            val romId = context.resources.getIdentifier(
                    romFileName,
                    "raw",
                    "es.chipeit.android"
            )

            return ResourceReader.readRawResourceAsBytes(context, romId)
        }
    }
}
