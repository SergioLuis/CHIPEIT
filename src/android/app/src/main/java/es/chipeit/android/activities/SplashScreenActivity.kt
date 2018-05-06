package es.chipeit.android.activities

import android.animation.Animator
import android.app.Activity
import android.graphics.Path
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_splashscreen.*

import yanzhikai.textpath.PathAnimatorListener
import yanzhikai.textpath.painter.AsyncPathPainter

import es.chipeit.android.R
import es.chipeit.android.ui.IntentFactory

class SplashScreenActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
    }

    override fun onStart() {
        super.onStart()
        splashScreenTitleTextView.setPathPainter(object: AsyncPathPainter {
            override fun onDrawPaintPath(x: Float, y: Float, paintPath: Path?) {
                paintPath?.addCircle(x, y, 10F, Path.Direction.CCW)
            }
        })

        splashScreenTitleTextView.setAnimatorListener(object : PathAnimatorListener() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                Handler().postDelayed({
                    startActivity(IntentFactory.forMainActivity(applicationContext))
                }, 500)
            }
        })

        splashScreenTitleTextView.startAnimation(0F, 1F)
    }
}
