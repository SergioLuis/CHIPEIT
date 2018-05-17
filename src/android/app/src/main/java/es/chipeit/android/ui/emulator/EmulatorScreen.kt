package es.chipeit.android.ui.emulator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.support.v4.content.res.ResourcesCompat
import android.util.AttributeSet
import android.view.View
import es.chipeit.android.R
import es.chipeit.lib.io.IGraphicMemory

class EmulatorScreen(context: Context, attrs: AttributeSet) : View(context, attrs) {
    val backgroundPaint = Paint()
    val foregroundPaint = Paint()

    lateinit var graphicMemory: IGraphicMemory
    private var isDrawing = false

    init {
        backgroundPaint.color = ResourcesCompat.getColor(
                context.resources,
                R.color.black,
                null
        )

        foregroundPaint.color = ResourcesCompat.getColor(
                context.resources,
                R.color.red,
                null
        )
    }

    fun redraw(graphicMemory: IGraphicMemory?) {
        if (graphicMemory == null)
            return

        if (isDrawing)
            return

        this.graphicMemory = graphicMemory
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (!::graphicMemory.isInitialized)
            return

        isDrawing = true

        val pixelWidth = width.toFloat() / graphicMemory.width.toFloat()
        val pixelHeight = height.toFloat() / graphicMemory.height.toFloat()

        for (row in 0 until graphicMemory.height) {
            for (column in 0 until graphicMemory.width) {
                val x = column * pixelWidth
                val y = row * pixelHeight

                canvas?.drawRect(
                        x,
                        y,
                        (x + pixelWidth),
                        (y + pixelHeight),
                        if (graphicMemory[column, row]) foregroundPaint else backgroundPaint
                )
            }
        }

        isDrawing = false
    }
}
