package es.chipeit.lib.emulator

import kotlin.math.max
import kotlin.math.min

import es.chipeit.lib.core.*
import es.chipeit.lib.core.hexfont.TABLE
import es.chipeit.lib.core.log.LoggedMemory
import es.chipeit.lib.interfaces.hzToMs
import es.chipeit.lib.io.ISwitchObserver

internal fun byteArrayCopy(src: ByteArray, dst: ByteArray) {
    for (i in src.indices)
        dst[i] = src[i]
}

class Chipeit(
        soundPlayer: ISwitchObserver,
        romContent: ByteArray,
        cpuClockRate: Short = 500,
        timersClockRate: Short = 60,
        private val sleeper: ISleeper
) {
    private val SCREEN_WIDTH_DIV_8 = 64 / 8
    val SCREEN_WIDTH = SCREEN_WIDTH_DIV_8 * 8
    val SCREEN_HEIGHT = 32

    private val _graphicMemory = ByteArray(SCREEN_WIDTH_DIV_8 * SCREEN_HEIGHT)
    val PUBLIC_GRAPHIC_MEMORY = ByteArray(SCREEN_WIDTH_DIV_8 * SCREEN_HEIGHT)

    @Volatile
    private var running: Boolean = false
    private val memory = LoggedMemory(
            "Main memory",
            ByteMemory(
                    TABLE +
                            ByteArray(0x200 - TABLE.size) +
                            romContent +
                            ByteArray(0x1000 - romContent.size - 0x200)
            )
    )
    private val graphicMemory = LoggedMemory(
            "Graphic memory",
            ByteMemory(_graphicMemory)
    )
    private val registers = Registers(LoggedMemory(
            "Registers memory",
            ByteMemory(ByteArray(16)))
    )
    private val stack = LoggedMemory(
            "Stack memory",
            ShortMemory(ShortArray(16))
    )

    private val soundTimer = Timer(soundPlayer)
    private val delayTimer = Timer()
    private val cpu = Cpu(
            registers,
            delayTimer,
            soundTimer,
            stack,
            memory,
            graphicMemory
    )

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

            byteArrayCopy(_graphicMemory, PUBLIC_GRAPHIC_MEMORY)

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
