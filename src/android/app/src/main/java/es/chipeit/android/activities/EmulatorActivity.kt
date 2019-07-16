package es.chipeit.android.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import es.chipeit.android.R
import es.chipeit.android.models.LibraryGame
import es.chipeit.android.ui.Fonts
import java.security.InvalidParameterException

@SuppressLint("ClickableViewAccessibility")
class EmulatorActivity : AppCompatActivity() {
    private lateinit var params: Params
    private lateinit var keyboardViewHolder: KeyboardViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        params = Params.fromBundle(intent.extras)
        if (params.game == null)
            throw InvalidParameterException("Params.game should not be null.")

        setTheme(params.game!!.themeId)

        setContentView(R.layout.activity_emulator)

        initializeView()
    }

    private fun initializeView() {
        initializeToolbar()
        initializeKeyboard()
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

    private fun initializeKeyboard() {
        keyboardViewHolder = KeyboardViewHolder(
                findViewById(R.id.activity_emulator_buttons_layout)
        )

        // FIXME: delete this crap, only for testing purposes
        val buttonIds = arrayOf(
                "ZERO",
                "ONE",
                "TWO",
                "THREE",
                "FOUR",
                "FIVE",
                "SIX",
                "SEVEN",
                "EIGHT",
                "NINE",
                "A",
                "B",
                "C",
                "D",
                "E",
                "F"
        )

        for (i in 0 until keyboardViewHolder.buttons.size) {
            Fonts.Companion.setTypeface(
                    keyboardViewHolder.buttons[i],
                    assets,
                    Fonts.PressStart2P
            )

            keyboardViewHolder.buttons[i].setOnTouchListener(
                    KeyboardButtonOnTouchListener(buttonIds[i])
            )
        }
    }

    private class KeyboardViewHolder(view: View) {
        val keyZeroButton: Button = view.findViewById(R.id.activity_emulator_key_zero_btn)
        val keyOneButton: Button = view.findViewById(R.id.activity_emulator_key_one_btn)
        val keyTwoButton: Button = view.findViewById(R.id.activity_emulator_key_two_btn)
        val keyThreeButton: Button = view.findViewById(R.id.activity_emulator_key_three_btn)
        val keyFourButton: Button = view.findViewById(R.id.activity_emulator_key_four_btn)
        val keyFiveButton: Button = view.findViewById(R.id.activity_emulator_key_five_btn)
        val keySixButton: Button = view.findViewById(R.id.activity_emulator_key_six_btn)
        val keySevenButton: Button = view.findViewById(R.id.activity_emulator_key_seven_btn)
        val keyEightButton: Button = view.findViewById(R.id.activity_emulator_key_eight_btn)
        val keyNineButton: Button = view.findViewById(R.id.activity_emulator_key_nine_btn)
        val keyAButton: Button = view.findViewById(R.id.activity_emulator_key_a_btn)
        val keyBButton: Button = view.findViewById(R.id.activity_emulator_key_b_btn)
        val keyCButton: Button = view.findViewById(R.id.activity_emulator_key_c_btn)
        val keyDButton: Button = view.findViewById(R.id.activity_emulator_key_d_btn)
        val keyEButton: Button = view.findViewById(R.id.activity_emulator_key_e_btn)
        val keyFButton: Button = view.findViewById(R.id.activity_emulator_key_f_btn)

        val buttons = arrayOf(
                keyZeroButton,
                keyOneButton,
                keyTwoButton,
                keyThreeButton,
                keyFourButton,
                keyFiveButton,
                keySixButton,
                keySevenButton,
                keyEightButton,
                keyNineButton,
                keyAButton,
                keyBButton,
                keyCButton,
                keyDButton,
                keyEButton,
                keyFButton
        )
    }

    private class KeyboardButtonOnTouchListener(
            private val keyId: String) : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            return when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    Toast.makeText(v?.context, "%s DOWN".format(keyId), Toast.LENGTH_SHORT).show()
                    v?.isPressed = true
                    true
                }

                MotionEvent.ACTION_UP -> {
                    Toast.makeText(v?.context, "%s UP".format(keyId), Toast.LENGTH_SHORT).show()
                    v?.isPressed = false
                    true
                }

                else -> false
            }
        }
    }

    class Params(val action: Action, val game: LibraryGame?) {
        enum class Action(val id: Int) {
            PLAY(0),
            RESUME(1)
        }

        companion object {
            fun fromBundle(bundle: Bundle): Params {
                return Params(
                        if (bundle.getInt(ActionKey) == 0) Action.PLAY else Action.RESUME,
                        LibraryGame.fromBundle(bundle)
                )
            }

            fun toBundle(params: Params, bundle: Bundle) {
                if (params.game == null)
                    throw InvalidParameterException("Game should not be null at this point")

                bundle.putInt(ActionKey, params.action.id)
                LibraryGame.toBundle(params.game, bundle)
            }

            private const val ActionKey = "ACTION"
        }
    }
}
