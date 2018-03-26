package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

import es.chipeit.lib.interfaces.ITimer
import es.chipeit.lib.io.ISwitchObserver
import org.mockito.Mockito

class TimerTests {
    @Test
    fun timerWithDefaultObserverTest() {
        val timer: ITimer = Timer()

        // assertNotFails equivalent
        timer.setRegister(1)
        timer.onClockTick()
    }

    @Test
    fun isTimerActiveTest() {
        val timer: ITimer = Timer()

        timer.setRegister(1)
        assertTrue(timer.isActive())
        timer.onClockTick()
        assertFalse(timer.isActive())
        timer.onClockTick()
        assertFalse(timer.isActive())
    }

    @Test
    fun observerEnableAndDisableTest() {
        val switchObserverMock = Mockito.mock(ISwitchObserver::class.java)
        val timer: ITimer = Timer(switchObserverMock)

        timer.setRegister(1) // switchObserverMock.onEnable()
        timer.onClockTick() // switchObserverMock.onDisable()
        timer.onClockTick() // nothing
        timer.setRegister(1) // switchObserverMock.onEnable()
        timer.setRegister(0) // switchObserverMock.onDisable()

        Mockito.verify(
                switchObserverMock,
                Mockito.times(2)
        ).onEnable()

        Mockito.verify(
                switchObserverMock,
                Mockito.times(2)
        ).onDisable()
    }
}
