package es.chipeit.lib.interfaces

internal interface IClock {
    val ms: Long
    fun update(): IClock
}
