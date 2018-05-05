package es.chipeit.lib.interfaces

internal interface IChronometer : IClock {
    val isRunning: Boolean

    fun start()
    fun stop()
}
