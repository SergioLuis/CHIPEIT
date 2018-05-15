package es.chipeit.android.models

import java.io.StringReader

import es.chipeit.android.R

class LibraryGame(
        val title: String,
        val file: String,
        val colorId: Int,
        var hasSavedContent: Boolean) {
    companion object {
        fun parse(fileContent: String): List<LibraryGame> {
            val result = ArrayList<LibraryGame>()
            var colorIndex = 0

            StringReader(fileContent).use {
                it.forEachLine {
                    val columns = it.split(":")
                    result.add(
                            LibraryGame(
                                    title = columns[0],
                                    file = columns[1],
                                    colorId = Colors[colorIndex++],
                                    hasSavedContent = false // TODO: save game status
                            )
                    )

                    if (colorIndex >= Colors.size)
                        colorIndex = 0
                }
            }

            return result
        }

        private val Colors = arrayOf(
                R.color.red,
                R.color.orange,
                R.color.yellow,
                R.color.green,
                R.color.blue,
                R.color.indigo,
                R.color.purple
        )
    }
}
