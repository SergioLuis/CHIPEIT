package es.chipeit.lib.interfaces

internal interface IRandomNumber {
    fun nextInt(min: Int, max: Int): Int
}