package es.chipeit.android.io

import java.io.InputStreamReader

import android.content.Context
import android.support.annotation.RawRes
import java.io.ByteArrayOutputStream

class
ResourceReader {
    companion object {
        fun readRawResourceAsString(context: Context, @RawRes resId: Int): String {
            context.resources.openRawResource(resId).use {
                InputStreamReader(it, "UTF-8").use {
                    return it.readText()
                }
            }
        }

        fun readRawResourceAsBytes(context: Context, @RawRes resId: Int): ByteArray {
            context.resources.openRawResource(resId).use {
                return it.readBytes(it.available())
            }
        }
    }
}
