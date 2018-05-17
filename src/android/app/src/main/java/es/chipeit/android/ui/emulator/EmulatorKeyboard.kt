package es.chipeit.android.ui.emulator

import android.widget.Button

class EmulatorKeyboard(
        val keyZero: Button, val keyOne: Button, val keyTwo: Button,
        val keyThree: Button, val keyFour: Button, val keyFive: Button,
        val keySix: Button, val keySeven: Button, val keyEight: Button,
        val keyNine: Button, val keyA: Button, val keyB: Button,
        val keyC: Button, val keyD: Button, val keyE: Button, val keyF: Button) {
    val keys = arrayOf(
            keyZero, keyOne, keyTwo, keyThree,
            keyFour, keyFive, keySix, keySeven,
            keyEight, keyNine, keyA, keyB,
            keyC, keyD, keyE, keyF
    )
}
