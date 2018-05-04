package es.chipeit.lib.core

import es.chipeit.lib.interfaces.IRandomNumber
import java.util.Random

internal class RandomNumber : IRandomNumber {
    val random = Random(System.currentTimeMillis())

    override fun nextInt(min: Int, max: Int): Int {
        return random.nextInt((max - min) + 1) + min
    }
}