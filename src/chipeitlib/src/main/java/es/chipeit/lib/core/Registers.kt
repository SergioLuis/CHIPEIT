package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

class Registers(override val vReg: IMemory<Byte>) : IRegisters {
    private val regI: ByteArray = ByteArray(2)
    private val regPC : ByteArray = ByteArray(2)

    private var regDT: Byte = 0x0
    private var regST: Byte = 0x0
    private var regSC: Byte = 0x0

    override fun getI(): ByteArray {
        return regI
    }

    override fun getDT(): Byte {
        return regDT
    }

    override fun setDT(value: Byte) {
        regDT = value
    }

    override fun getST(): Byte {
        return regST
    }

    override fun setST(value: Byte) {
        regSC = value
    }

    override fun getPC(): ByteArray {
        return regPC
    }

    override fun getSC(): Byte {
        return regSC
    }

    override fun setSC(value: Byte) {
        regSC = value
    }
}