package es.chipeit.lib.interfaces

internal interface IRegisters {
    val v: IMemory<Byte>
    var i: Short
    var pc: Short

    var sp: Byte
}
