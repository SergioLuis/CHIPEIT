package es.chipeit.lib.emulator

import kotlin.math.max
import kotlin.math.min

import es.chipeit.lib.core.*
import es.chipeit.lib.core.hexfont.TABLE
import es.chipeit.lib.core.log.LoggedMemory
import es.chipeit.lib.interfaces.hzToMs
import es.chipeit.lib.io.IGraphicMemory
import es.chipeit.lib.io.ISwitchObserver
import es.chipeit.lib.core.Keyboard
import es.chipeit.lib.core.log.LoggedKeyboard
import es.chipeit.lib.io.IUserKeyboard

class Chipeit(
        soundPlayer: ISwitchObserver,
        romContent: ByteArray,
        cpuClockRate: Short = 500,
        timersClockRate: Short = 60,
        private val sleeper: ISleeper
) {
    @Volatile
    private var running: Boolean = false
    private val registers = Registers(LoggedMemory(
            "Registers memory",
            ByteMemory(ByteArray(16)))
    )
    private val delayTimer = Timer()
    private val soundTimer = Timer(soundPlayer)
    private val stack = LoggedMemory(
            "Stack memory",
            IntMemory(IntArray(16))
    )
    private val memory = LoggedMemory(
            "Main memory",
            ByteMemory(
                    TABLE +
                            ByteArray(0x200 - TABLE.size) +
                            romContent +
                            ByteArray(0x1000 - romContent.size - 0x200)
            )
    )

    private val _graphicMemory = GraphicMemory(
            Array(Constants.ScreenHeight) { Array(Constants.ScreenWidth) { false } })

    private val _keyboard = LoggedKeyboard(Keyboard(soundTimer))

    private val cpu = Cpu(
            registers,
            delayTimer,
            soundTimer,
            stack,
            memory,
            _graphicMemory,
            _keyboard
    )

    val UserKeyboard: IUserKeyboard = _keyboard
    val GraphicMemory: IGraphicMemory = _graphicMemory

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
