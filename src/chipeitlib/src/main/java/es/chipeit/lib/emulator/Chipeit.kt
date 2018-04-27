package es.chipeit.lib.emulator

import kotlin.math.max
import kotlin.math.min

import es.chipeit.lib.core.*
import es.chipeit.lib.core.hexfont.TABLE
import es.chipeit.lib.core.log.LoggedMemory
import es.chipeit.lib.interfaces.hzToMs
import es.chipeit.lib.io.IGraphicMemory
import es.chipeit.lib.io.ISwitchObserver

class Chipeit(
        soundPlayer: ISwitchObserver,
        romContent: ByteArray,
        cpuClockRate: Short = 500,
        timersClockRate: Short = 60,
        private val sleeper: ISleeper
) {
    val SCREEN_WIDTH = 64
    val SCREEN_HEIGHT = 32

    private val _graphicMemory = GraphicMemory(Array(SCREEN_WIDTH) { Array(SCREEN_HEIGHT) { false } })
    val graphicMemory: IGraphicMemory = _graphicMemory

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
            _graphicMemory
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
