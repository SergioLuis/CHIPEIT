package es.chipeit.console

import es.chipeit.lib.io.IRenderer

class DummyRenderer : IRenderer {
    override fun render(graphicsMemory: ByteArray) {
        println("Render!")
    }
}
