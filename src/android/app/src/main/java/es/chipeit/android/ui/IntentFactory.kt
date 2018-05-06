package es.chipeit.android.ui

import android.content.Context
import android.content.Intent
import es.chipeit.android.activities.MainActivity

class IntentFactory {
    companion object {
        fun forMainActivity(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}
