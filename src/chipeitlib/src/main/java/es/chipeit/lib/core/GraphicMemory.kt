package es.chipeit.lib.core

import es.chipeit.lib.interfaces.ICoreGraphicMemory

internal class GraphicMemory(private val memory: Array<Array<Boolean>>) : ICoreGraphicMemory {
    override val width: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override val height: Int
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

    override fun get(x: Int, y: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawLine(x: Int, y: Int, value: Byte) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun clear() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
