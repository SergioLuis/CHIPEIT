package es.chipeit.lib.core

import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times

import es.chipeit.lib.interfaces.IRegisters

class InstructionSetTests {
    @Test
    fun jpAddrTest() {
        val registersMock = Mockito.mock(IRegisters::class.java)

        jpAddr(0x1225, registersMock)
        jpAddr(0x1512, registersMock)
        jpAddr(0x1FFF, registersMock)

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0225

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0512

        Mockito.verify(
                registersMock,
                times(1)
        ).pc = 0x0FFF
    }
}
