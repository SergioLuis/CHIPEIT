package es.chipeit.android.io

import java.io.InputStreamReader

import android.content.Context
import android.support.annotation.RawRes

class ResourceReader {
    companion object {
        fun readRawResourceAsString(context: Context, @RawRes resId: Int): String {
            var fileContent: String
            context.resources.openRawResource(resId).use {
                InputStreamReader(it, "UTF-8").use {
                    return it.readText()
                }
            }
        }
    }
}
