package es.chipeit.lib.extensions

internal val Byte.Companion.ZERO: Byte
    get() = 0x0

internal fun Int.nonNegativeRem(divisor: Int): Int {
    var rem = this % divisor
    if(rem < 0)
        rem += divisor
    return rem
}
