package es.chipeit.android.activities

import java.security.InvalidParameterException

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

import es.chipeit.android.R
import es.chipeit.android.models.LibraryGame
import es.chipeit.android.ui.Fonts
import es.chipeit.android.ui.emulator.ChipeitEmulator
import es.chipeit.android.ui.emulator.EmulatorKeyboard
import es.chipeit.android.ui.emulator.EmulatorScreen

class EmulatorActivity : AppCompatActivity() {
    private lateinit var params: Params
    private lateinit var emulator: ChipeitEmulator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        params = Params.fromBundle(intent.extras)
        if (params.game == null)
            throw InvalidParameterException("Params.game should not be null.")

        setTheme(params.game!!.themeId)
        setContentView(R.layout.activity_emulator)

        initializeToolbar()
        initializeEmulator()

        emulator.start()
    }

    override fun onPause() {
        super.onPause()
        emulator.stop()
    }

    private fun initializeToolbar() {
        val titleTextView: TextView = findViewById(
                R.id.activity_emulator_toolbar_title_txt
        )
        titleTextView.text = params.game!!.title
        Fonts.Companion.setTypeface(titleTextView, assets, Fonts.PressStart2P)
        titleTextView.setTextColor(
                ResourcesCompat.getColor(
                        resources,
                        R.color.white,
                        null
                )
        )
    }

    private fun initializeEmulator() {
        val emulatorKeyboard = EmulatorKeyboard(
                findViewById(R.id.activity_emulator_key_zero_btn),
                findViewById(R.id.activity_emulator_key_one_btn),
                findViewById(R.id.activity_emulator_key_two_btn),
                findViewById(R.id.activity_emulator_key_three_btn),
                findViewById(R.id.activity_emulator_key_four_btn),
                findViewById(R.id.activity_emulator_key_five_btn),
                findViewById(R.id.activity_emulator_key_six_btn),
                findViewById(R.id.activity_emulator_key_seven_btn),
                findViewById(R.id.activity_emulator_key_eight_btn),
                findViewById(R.id.activity_emulator_key_nine_btn),
                findViewById(R.id.activity_emulator_key_a_btn),
                findViewById(R.id.activity_emulator_key_b_btn),
                findViewById(R.id.activity_emulator_key_c_btn),
                findViewById(R.id.activity_emulator_key_d_btn),
                findViewById(R.id.activity_emulator_key_e_btn),
                findViewById(R.id.activity_emulator_key_f_btn)
        )

        val emulatorScreen: EmulatorScreen = findViewById(
                R.id.activity_emulator_screen_view
        )

        emulator = ChipeitEmulator(
                this,
                params.game!!.file,
                emulatorKeyboard,
                emulatorScreen,
                params.enableLoadStoreQuirk,
                params.enableShiftQuirk
        )
    }

    class Params(
            val action: Action,
            val enableLoadStoreQuirk: Boolean,
            val enableShiftQuirk: Boolean,
            val game: LibraryGame?) {
        enum class Action(val id: Int) {
            PLAY(0),
            RESUME(1)
        }

        companion object {
            fun fromBundle(bundle: Bundle): Params {
                return Params(
                        if (bundle.getInt(ActionKey) == 0) Action.PLAY else Action.RESUME,
                        bundle.getBoolean(LoadStoreQuirkKey),
                        bundle.getBoolean(ShiftQuirkKey),
                        LibraryGame.fromBundle(bundle)
                )
            }

            fun toBundle(params: Params, bundle: Bundle) {
                if (params.game == null)
                    throw InvalidParameterException("Game should not be null at this point")

                bundle.putInt(ActionKey, params.action.id)
                bundle.putBoolean(LoadStoreQuirkKey, params.enableLoadStoreQuirk)
                bundle.putBoolean(ShiftQuirkKey, params.enableShiftQuirk)
                LibraryGame.toBundle(params.game, bundle)
            }

            private const val ActionKey = "ACTION"
            private const val LoadStoreQuirkKey = "LOAD_STORE_QUIRK"
            private const val ShiftQuirkKey = "SHIFT_QUIRK"
        }
    }
}
