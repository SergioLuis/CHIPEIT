package es.chipeit.android.activities

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import es.chipeit.android.R
import es.chipeit.android.ui.Fonts
import es.chipeit.android.ui.Intents

class LegacySplashScreenActivity : AppCompatActivity() {
    private lateinit var titleTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_legacysplashscreen)

        initializeTitle()

        Handler().postDelayed({
            Intents.startGameLibraryActivity(this)
        }, resources.getInteger(R.integer.splash_to_main_delay).toLong())
    }

    private fun initializeTitle() {
        titleTextView = findViewById(R.id.activity_legacysplashscreen_title_txt)

        Fonts.Companion.setTypeface(
                titleTextView,
                assets,
                Fonts.PressStart2P
        )
    }
}
