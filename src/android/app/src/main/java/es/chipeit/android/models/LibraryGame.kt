package es.chipeit.android.models

import android.os.Bundle
import java.io.StringReader

import es.chipeit.android.R

class LibraryGame(
        val title: String,
        val file: String,
        val colorId: Int,
        val themeId: Int,
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
                                    colorId = Colors[colorIndex],
                                    themeId = Themes[colorIndex],
                                    hasSavedContent = false // TODO: save game status
                            )
                    )

                    if (++colorIndex >= Colors.size)
                        colorIndex = 0
                }
            }

            return result
        }

        fun toBundle(libraryGame: LibraryGame, bundle: Bundle) {
            bundle.putString(TitleKey, libraryGame.title)
            bundle.putString(FileKey, libraryGame.file)
            bundle.putInt(ColorIdKey, libraryGame.colorId)
            bundle.putInt(ThemeIdKey, libraryGame.themeId)
            bundle.putBoolean(SavedContentKey, libraryGame.hasSavedContent)
        }

        fun fromBundle(bundle: Bundle): LibraryGame {
            return LibraryGame(
                    bundle.getString(TitleKey),
                    bundle.getString(FileKey),
                    bundle.getInt(ColorIdKey),
                    bundle.getInt(ThemeIdKey),
                    bundle.getBoolean(SavedContentKey)
            )
        }

        private const val TitleKey = "TITLE"
        private const val FileKey = "FILE"
        private const val ColorIdKey = "COLOR_ID"
        private const val ThemeIdKey = "THEME_ID"
        private const val SavedContentKey = "SAVED_CONTENT"

        private val Colors = arrayOf(
                R.color.red,
                R.color.orange,
                R.color.yellow,
                R.color.green,
                R.color.blue,
                R.color.indigo,
                R.color.purple
        )

        private val Themes = arrayOf(
                R.style.RedTheme,
                R.style.OrangeTheme,
                R.style.YellowTheme,
                R.style.GreenTheme,
                R.style.BlueTheme,
                R.style.IndigoTheme,
                R.style.PurpleTheme
        )
    }
}
