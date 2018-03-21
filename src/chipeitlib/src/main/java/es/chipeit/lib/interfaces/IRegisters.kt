package es.chipeit.lib.interfaces

internal interface IRegisters {
    val vReg : IMemory<Byte>

    fun getI(): ByteArray

    fun getDT(): Byte
    fun setDT(value: Byte)

    fun getST(): Byte
    fun setST(value: Byte)

    fun getPC(): ByteArray

    fun getSC(): Byte
    fun setSC(value: Byte)
}