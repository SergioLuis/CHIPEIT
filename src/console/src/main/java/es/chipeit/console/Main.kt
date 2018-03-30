package es.chipeit.console

import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

import es.chipeit.lib.emulator.Chipeit

fun main(args: Array<String>) {
    if (args.size != 1) {
        System.err.println(USAGE)
        System.exit(1)
    }

    val romResult = tryReadFile(args[0])

    if (!romResult.first) {
        System.err.println("There was an error reading the file.")
        System.exit(1)
    }

    val chipeit = Chipeit(
            DummyRenderer(),
            DummySoundPlayer(),
            romResult.second,
            10,
            60,
            DummySleeper()
    )

    chipeit.run()
}

fun tryReadFile(path: String): Pair<Boolean, ByteArray> {
    try {
        return Pair(true, Files.readAllBytes(Paths.get(path)))
    }
    catch (e: IOException) {
        return Pair(false, ByteArray(0))
    }
}

val USAGE: String = "Usage: java -jar console.jar <ROM path>"
