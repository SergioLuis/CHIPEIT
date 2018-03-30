package es.chipeit.lib.emulator

import kotlin.math.max
import kotlin.math.min

import es.chipeit.lib.core.*
import es.chipeit.lib.core.log.LoggedMemory
import es.chipeit.lib.interfaces.hzToMs
import es.chipeit.lib.io.IRenderer
import es.chipeit.lib.io.ISwitchObserver

class Chipeit(
        renderer: IRenderer,
        soundPlayer: ISwitchObserver,
        romContent: ByteArray,
        cpuClockRate: Short = 500,
        timersClockRate: Short = 60,
        private val sleeper: ISleeper
) {
    @Volatile
    private var running: Boolean = false
    private val memory = LoggedMemory(
            "Main memory",
            PaddedMemory(ByteMemory(romContent), 0x200)
    )
    private val registers = Registers(LoggedMemory(
            "Registers memory",
            ByteMemory(ByteArray(16)))
    )
    private val stack = LoggedMemory(
            "Stack memory",
            ShortMemory(ShortArray(16))
    )

    private val cpu = Cpu(memory, registers, stack, renderer)
    private val soundTimer = Timer(soundPlayer)
    private val eventTimer = Timer() // FIXME: I still don't know the f*cking purpose of this

    private val clock = Clock()

    private val cpuClockDivider = ClockDivider(clock, hzToMs(cpuClockRate))
    private val timersClockDivider = ClockDivider(clock, hzToMs(timersClockRate))

    init {
        cpuClockDivider.observers.add(cpu)
        timersClockDivider.observers.add(soundTimer)
        timersClockDivider.observers.add(eventTimer)
    }

    fun run() {
        running = true
        cpuClockDivider.init()
        timersClockDivider.init()

        while (running) {
            cpuClockDivider.trigger()
            timersClockDivider.trigger()

            val timeToSleep =
                max(0, min(
                    cpuClockDivider.getMsLeft(),
                    timersClockDivider.getMsLeft())
                )

            sleeper.sleep(timeToSleep)
        }
    }

    fun stop() {
        running = false
    }
}
