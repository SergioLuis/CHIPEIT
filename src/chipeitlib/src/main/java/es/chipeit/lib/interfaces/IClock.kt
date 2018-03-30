package es.chipeit.lib.interfaces

internal interface IClock {
    var ms: Long
    fun update()
}
