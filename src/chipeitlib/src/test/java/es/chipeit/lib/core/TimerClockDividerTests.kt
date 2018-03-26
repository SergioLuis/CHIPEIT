package es.chipeit.lib.core

import java.lang.IllegalArgumentException

import org.junit.Test
import kotlin.test.assertFailsWith
import org.mockito.invocation.InvocationOnMock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.stubbing.Answer

import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.ITimer

class TimerClockDividerTests {
    @Test
    fun timerClockDividerWithInvalidStepTest() {
        val clock = Mockito.mock(IClock::class.java)

        assertFailsWith<IllegalArgumentException> {
            TimerClockDivider(clock, 0)
        }

        assertFailsWith<IllegalArgumentException> {
            TimerClockDivider(clock, -1)
        }
    }

    @Test
    fun timerClockDividerWithoutTimersTest() {
        val clockAnswer = object: Answer<Long> {
            var times = 0L

            override fun answer(invocation: InvocationOnMock?): Long {
                return times
            }
        }

        val clock = Mockito.mock(IClock::class.java)
        Mockito.`when`(clock.getMs()).thenAnswer(clockAnswer)

        val timerClockDivider = TimerClockDivider(clock, 1000)

        for (i in 1..2000) {
            clockAnswer.times = i.toLong()

            // assertNotFails equivalent
            timerClockDivider.trigger()
        }
    }

    @Test
    fun timerClockDividerWithTimersTest() {
        val clockAnswer = object: Answer<Long> {
            var times = 0L

            override fun answer(invocation: InvocationOnMock?): Long {
                return times
            }
        }

        val clock = Mockito.mock(IClock::class.java)
        Mockito.`when`(clock.getMs()).thenAnswer(clockAnswer)

        val timerClockDivider = TimerClockDivider(clock, 500)
        val firstTimerMock = Mockito.mock(ITimer::class.java)
        val secondTimerMock = Mockito.mock(ITimer::class.java)

        timerClockDivider.timers.add(firstTimerMock)
        timerClockDivider.timers.add(secondTimerMock)

        for (i in 1..2000) {
            clockAnswer.times = i.toLong()
            timerClockDivider.trigger()
        }

        Mockito.verify(
                firstTimerMock,
                times(4)
        ).decrementRegister()

        Mockito.verify(
                secondTimerMock,
                times(4)
        ).decrementRegister()
    }
}
