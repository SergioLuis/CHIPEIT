package es.chipeit.lib.interfaces

internal interface IRegisters {
    val v: IMemory<Byte>
    var i: Int
    var pc: Int

    var sp: Int
}
