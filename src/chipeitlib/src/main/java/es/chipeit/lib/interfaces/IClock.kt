package es.chipeit.lib.interfaces

internal interface IClock {
    fun update(): IClock
    fun getMs(): Long
}
