package es.chipeit.lib.io

interface IUserKeyboard {
    class KeyData(val id: Byte, val flags: Int)

    enum class Keys(val data: KeyData) {
        KEY_0(KeyData(0x0, 1 shl 0)),
        KEY_1(KeyData(0x1, 1 shl 1)),
        KEY_2(KeyData(0x2, 1 shl 2)),
        KEY_3(KeyData(0x3, 1 shl 3)),
        KEY_4(KeyData(0x4, 1 shl 4)),
        KEY_5(KeyData(0x5, 1 shl 5)),
        KEY_6(KeyData(0x6, 1 shl 6)),
        KEY_7(KeyData(0x7, 1 shl 7)),
        KEY_8(KeyData(0x8, 1 shl 8)),
        KEY_9(KeyData(0x9, 1 shl 9)),
        KEY_A(KeyData(0xA, 1 shl 10)),
        KEY_B(KeyData(0xB, 1 shl 11)),
        KEY_C(KeyData(0xC, 1 shl 12)),
        KEY_D(KeyData(0xD, 1 shl 13)),
        KEY_E(KeyData(0xE, 1 shl 14)),
        KEY_F(KeyData(0xF, 1 shl 15)),
        NONE(KeyData(-1, 0))
    }

    fun pressKey(key: Keys)
    fun releaseKey(key: Keys)
}
