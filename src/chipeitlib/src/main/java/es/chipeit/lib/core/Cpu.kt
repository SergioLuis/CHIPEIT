package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IClockObserver
import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

internal class Cpu(
        private val memory: IMemory<Byte>,
        private val registers: IRegisters
) : IClockObserver {
    override fun onClockTick() {
        var instruction = memory[registers.pc.toInt()]
        // TODO: Decode and execute.
    }
}
