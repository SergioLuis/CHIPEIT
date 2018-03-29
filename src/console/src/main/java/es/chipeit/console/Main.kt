package es.chipeit.console

import es.chipeit.lib.emulator.Chipeit

fun main(args: Array<String>) {
    println(args.size)
    for (arg in args) {
        println(arg)
    }

    var emulator: Chipeit = Chipeit()
}
