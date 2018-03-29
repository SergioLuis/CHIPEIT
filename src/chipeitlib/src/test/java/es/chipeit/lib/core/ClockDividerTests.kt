package es.chipeit.lib.core

import java.lang.IllegalArgumentException

import org.junit.Test
import kotlin.test.assertFailsWith
import org.mockito.invocation.InvocationOnMock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.stubbing.Answer

import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.IClockObserver

class ClockDividerTests {
    @Test
    fun clockDividerWithInvalidStepTest() {
        val clock = Mockito.mock(IClock::class.java)

        assertFailsWith<IllegalArgumentException> {
            ClockDivider(clock, 0)
        }

        assertFailsWith<IllegalArgumentException> {
            ClockDivider(clock, -1)
        }
    }

    @Test
    fun clockDividerWithoutObserversTest() {
        val clockAnswer = object: Answer<Long> {
            var ms = 0L

            override fun answer(invocation: InvocationOnMock?): Long {
                return ms
            }
        }

        val clock = Mockito.mock(IClock::class.java)
        Mockito.`when`(clock.getMs()).thenAnswer(clockAnswer)

        val clockDivider = ClockDivider(clock, 1000)

        clockDivider.init()
        for (i in 1..2000) {
            clockAnswer.ms = i.toLong()

            // assertNotFails equivalent
            clockDivider.trigger()
        }
    }

    @Test
    fun clockDividerWithObserversTest() {
        val clockAnswer = object: Answer<Long> {
            var ms = 0L

            override fun answer(invocation: InvocationOnMock?): Long {
                return ms
            }
        }

        val clock = Mockito.mock(IClock::class.java)
        Mockito.`when`(clock.getMs()).thenAnswer(clockAnswer)

        val clockDivider = ClockDivider(clock, 500)
        val firstClockObserverMock = Mockito.mock(IClockObserver::class.java)
        val secondClockObserverMock = Mockito.mock(IClockObserver::class.java)

        clockDivider.observers.add(firstClockObserverMock)
        clockDivider.observers.add(secondClockObserverMock)

        clockDivider.init()
        for (i in 1..2000) {
            clockAnswer.ms = i.toLong()
            clockDivider.trigger()
        }

        Mockito.verify(
                firstClockObserverMock,
                times(4)
        ).onClockTick()

        Mockito.verify(
                secondClockObserverMock,
                times(4)
        ).onClockTick()
    }

    @Test
    fun clockDividerSlowTest() {
        val clockAnswer = object: Answer<Long> {
            var ms = 0L

            override fun answer(invocation: InvocationOnMock?): Long {
                return ms
            }
        }

        val clock = Mockito.mock(IClock::class.java)
        Mockito.`when`(clock.getMs()).thenAnswer(clockAnswer)

        val clockDivider = ClockDivider(clock, 1)
        val firstClockObserverMock = Mockito.mock(IClockObserver::class.java)

        clockDivider.observers.add(firstClockObserverMock)

        clockAnswer.ms = 10

        clockDivider.init()
        while(clockDivider.getMsLeft() <= 0L)
            clockDivider.trigger()

        Mockito.verify(
                firstClockObserverMock,
                times(10)
        ).onClockTick()
    }
}
