package es.chipeit.android.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import es.chipeit.android.activities.EmulatorActivity

import es.chipeit.android.activities.GameLibraryActivity

class Intents {
    companion object {
        fun startGameLibraryActivity(srcActivity: Activity) {
            srcActivity.startActivity(
                    Intent(srcActivity, GameLibraryActivity::class.java),
                    ActivityOptions.makeCustomAnimation(
                            srcActivity,
                            android.R.anim.fade_in,
                            android.R.anim.fade_out
                    ).toBundle()
            )
        }

        fun startEmulatorActivity(srcActivity: Activity, params: EmulatorActivity.Params) {
            val bundle = Bundle()
            val intent = Intent(srcActivity, EmulatorActivity::class.java)
            EmulatorActivity.Params.toBundle(params, bundle)
            intent.putExtras(bundle)

            srcActivity.startActivity(intent)
        }
    }
}
