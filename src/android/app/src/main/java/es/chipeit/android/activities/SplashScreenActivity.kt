package es.chipeit.android.activities

import android.animation.Animator
import android.graphics.Path
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

import yanzhikai.textpath.AsyncTextPathView
import yanzhikai.textpath.PathAnimatorListener
import yanzhikai.textpath.painter.AsyncPathPainter

import es.chipeit.android.R
import es.chipeit.android.ui.Fonts
import es.chipeit.android.ui.Intents

class SplashScreenActivity : AppCompatActivity() {
    private lateinit var titleTextView: AsyncTextPathView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        initializeTitle()
    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed({
            titleTextView.startAnimation(
                    AnimationBegin,
                    AnimationEnd
            )
        }, 100)
    }

    private fun initializeTitle() {
        titleTextView = findViewById(R.id.activity_splashscreen_title_txt)

        Fonts.Companion.setTypeface(
                titleTextView,
                assets,
                Fonts.PressStart2P
        )

        // Workaround, the library doesn't recalculate the path to draw once the Typeface changes
        titleTextView.setText(getString(R.string.app_name))
        titleTextView.setPathPainter(TitleAsyncPathPainter(10F))
        titleTextView.setAnimatorListener(
                TitleAnimatorListener(
                        titleTextView,
                        { startMainActivity() }
                )
        )
    }

    private fun startMainActivity() {
        Handler().postDelayed({
            Intents.startGameLibraryActivity(this)
        }, resources.getInteger(R.integer.splash_to_main_delay).toLong())
    }

    private class TitleAsyncPathPainter(private val circleRadius: Float) : AsyncPathPainter {
        override fun onDrawPaintPath(x: Float, y: Float, paintPath: Path?) {
            paintPath?.addCircle(x, y, circleRadius, Path.Direction.CCW)
        }
    }

    private class TitleAnimatorListener(
            private val textView: AsyncTextPathView,
            private val action: () -> Unit) : PathAnimatorListener() {
        override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
            super.onAnimationEnd(animation, isReverse)

            textView.showFillColorText()
            action()
        }
    }

    companion object {
        private val AnimationBegin = 0F
        private val AnimationEnd = 1F
    }
}
