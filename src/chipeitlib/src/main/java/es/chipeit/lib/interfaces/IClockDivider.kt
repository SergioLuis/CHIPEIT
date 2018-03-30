package es.chipeit.lib.interfaces

import kotlin.math.round

internal fun hzToMs(hz: Short): Long {
    if (hz <= 0)
        throw IllegalArgumentException("Non-positive hz ($hz)")

    return round(1000.0 / hz).toLong()
}

internal fun msToHz(ms: Long): Short {
    if (ms <= 0)
        throw IllegalArgumentException("Non-positive ms ($ms)")

    return round(1000.0 / ms).toShort()
}

internal interface IClockDivider {
    var msPerStep: Long
    val msLeft: Long

    fun reset()
    fun trigger()
}
