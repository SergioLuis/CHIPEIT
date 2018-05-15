package es.chipeit.android.ui

import kotlin.collections.HashMap

import android.content.res.AssetManager
import android.graphics.Typeface
import android.widget.TextView

import yanzhikai.textpath.TextPathView

class Fonts {
    companion object {
        fun createTypeface(assets: AssetManager, typeface: String): Typeface? {
            if (typefaces[typeface] == null) {
                typefaces[typeface] = Typeface.createFromAsset(assets, typeface)
            }

            return typefaces[typeface]
        }

        fun setTypeface(textView: TextView, assets: AssetManager, typeface: String) {
            textView.typeface = createTypeface(assets, typeface)
        }

        fun setTypeface(textView: TextPathView, assets: AssetManager, typeface: String) {
            if (typefaces[typeface] == null) {
                typefaces[typeface] = Typeface.createFromAsset(assets, typeface)
            }

            textView.setTypeface(typefaces[typeface])
        }

        val PressStart2P = "PressStart2P.ttf"

        private val typefaces = HashMap<String, Typeface>()
    }
}
