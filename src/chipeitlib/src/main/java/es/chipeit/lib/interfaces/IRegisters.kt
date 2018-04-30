package es.chipeit.lib.interfaces

internal interface IRegisters {
    val v: IMemory<Byte>
    var i: Short
    var pc: Int

    var sp: Int
}
