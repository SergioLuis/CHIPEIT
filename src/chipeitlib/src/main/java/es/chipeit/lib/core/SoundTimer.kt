package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IRegisters
import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.ISoundPlayer

internal class SoundTimer(registers: IRegisters, soundPlayer: ISoundPlayer) : ITimer {
    private val registers = registers
    private val soundPlayer = soundPlayer

    /* FIXME:
        It's a shame that Kotlin doesn't allow creating a Byte object easily
        The comparison inside the isActive method should be doable
        with the help of an extension of the class Byte
        so the read-only property ZERO would be added.

        Figuring how do this (and moving the property there) is your task. Good luck.
    */
    private val ZERO: Byte = 0x0

    override fun isActive(): Boolean = registers.st != ZERO

    override fun setRegister(value: Byte) {
        registers.st = value
        if (isActive()) {
            soundPlayer.start()
        }
    }

    override fun decrementRegister() {
        if (isActive()) {
            registers.st--
            return
        }
        soundPlayer.stop()
    }
}