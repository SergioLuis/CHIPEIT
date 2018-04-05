package es.chipeit.lib.emulator

import kotlin.math.max
import kotlin.math.min

import es.chipeit.lib.core.*
import es.chipeit.lib.core.log.LoggedMemory
import es.chipeit.lib.interfaces.hzToMs
import es.chipeit.lib.io.ISwitchObserver
import es.chipeit.lib.core.Keyboard
import es.chipeit.lib.core.log.LoggedKeybard
import es.chipeit.lib.io.IKeyboard

internal fun byteArrayCopy(src: ByteArray, dst: ByteArray) {
    for(i in src.indices)
        dst[i] = src[i]
}

class Chipeit(
        soundPlayer: ISwitchObserver,
        romContent: ByteArray,
        cpuClockRate: Short = 500,
        timersClockRate: Short = 60,
        private val sleeper: ISleeper
) {
    val SCREEN_WIDTH = 64
    val SCREEN_HEIGHT = 32

    private val _graphicsMemory = ByteArray(SCREEN_WIDTH * SCREEN_HEIGHT)
    val PUBLIC_GRAPHICS_MEMORY = ByteArray(SCREEN_WIDTH * SCREEN_HEIGHT)

    @Volatile
    private var running: Boolean = false
    private val memory = LoggedMemory(
            "Main memory",
            PaddedMemory(ByteMemory(romContent), 0x200)
    )

    private val graphicMemory = LoggedMemory(
            "Graphic memory",
            ByteMemory(_graphicsMemory)
    )
    private val registers = Registers(LoggedMemory(
            "Registers memory",
            ByteMemory(ByteArray(16)))
    )
    private val stack = LoggedMemory(
            "Stack memory",
            IntMemory(IntArray(16))
    )

    private val keyboard = Keyboard()
    private val loggedKeyboard = LoggedKeybard(keyboard)

    val UserKeyboard: IKeyboard
        get() = loggedKeyboard

    private val cpu = Cpu(memory, graphicMemory, registers, stack, keyboard)
    private val soundTimer = Timer(soundPlayer)
    private val delayTimer = Timer()

    private val chronometer = Chronometer(Clock())

    private val cpuClockDivider = ClockDivider(chronometer, hzToMs(cpuClockRate))
    private val timersClockDivider = ClockDivider(chronometer, hzToMs(timersClockRate))

    init {
        cpuClockDivider.observers.add(cpu)
        timersClockDivider.observers.add(soundTimer)
        timersClockDivider.observers.add(delayTimer)
    }

    fun run() {
        chronometer.start()

        while (chronometer.isRunning) {
            chronometer.update()

            cpuClockDivider.trigger()
            timersClockDivider.trigger()

            byteArrayCopy(_graphicsMemory, PUBLIC_GRAPHICS_MEMORY)

            val timeToSleep =
                max(0, min(
                    cpuClockDivider.msLeft,
                    timersClockDivider.msLeft)
                )

            sleeper.sleep(timeToSleep)
        }
    }

    fun stop() {
        chronometer.stop()
    }
}
