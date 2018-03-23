package es.chipeit.lib.interfaces

internal interface IClock {
    fun update()
    fun getMs(): Long
}
