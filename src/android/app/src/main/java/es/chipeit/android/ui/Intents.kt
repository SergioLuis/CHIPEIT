package es.chipeit.android.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent

import es.chipeit.android.activities.GameLibraryActivity

class Intents {
    companion object {
        fun startMainActivity(activity: Activity) {
            activity.startActivity(
                    Intent(activity, GameLibraryActivity::class.java),
                    ActivityOptions.makeCustomAnimation(
                            activity,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    ).toBundle()
            )
        }
    }
}
