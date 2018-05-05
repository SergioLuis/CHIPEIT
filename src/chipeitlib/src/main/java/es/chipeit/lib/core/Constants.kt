package es.chipeit.lib.core

class Constants {
    private constructor()

    companion object {
        internal const val HexFontStart = 0x000
        internal const val CharacterSpriteLength = 5

        internal const val MemorySize = 0x1000
        internal const val ProtectedMemorySize = 0x0200
        internal const val StackDepth = 16
        internal const val VRegistersCount = 16

        internal const val CodeStartAddress = 0x200

        const val ScreenWidth = 64
        const val ScreenHeight = 32
    }
}