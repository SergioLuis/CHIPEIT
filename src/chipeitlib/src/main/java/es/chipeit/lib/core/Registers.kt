package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IMemory
import es.chipeit.lib.interfaces.IRegisters

internal class Registers(override val v: IMemory<Byte>) : IRegisters {
    override var i: Short = 0x00
    override var pc: Short = 0x00

    override var sp: Byte = 0x0

    init {
        if(v.size != 16)
            throw IllegalArgumentException("Parameter v has ${v.size} elements, 16 needed")
    }
}
