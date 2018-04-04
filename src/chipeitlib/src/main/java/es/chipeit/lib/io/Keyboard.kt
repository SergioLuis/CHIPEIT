package es.chipeit.lib.io

class Keyboard {
    enum class Keys(val id: Int) {
        KEY_0(1 shl 0),
        KEY_1(1 shl 1),
        KEY_2(1 shl 2),
        KEY_3(1 shl 3),
        KEY_4(1 shl 4),
        KEY_5(1 shl 5),
        KEY_6(1 shl 6),
        KEY_7(1 shl 7),
        KEY_8(1 shl 8),
        KEY_9(1 shl 9),
        KEY_A(1 shl 10),
        KEY_B(1 shl 11),
        KEY_C(1 shl 12),
        KEY_D(1 shl 13),
        KEY_E(1 shl 14),
        KEY_F(1 shl 15)
    }

    private var _keysDown: Int = 0

    val keysDown: Int
        get() = _keysDown

    fun keyDown(key: Keys) {
        _keysDown = _keysDown or key.id
    }

    fun keyUp(key: Keys) {
        _keysDown = _keysDown and key.id.inv()
    }
}
