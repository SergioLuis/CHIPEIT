package es.chipeit.lib.core

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.mockito.invocation.InvocationOnMock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.stubbing.Answer

import es.chipeit.lib.interfaces.IChronometer
import es.chipeit.lib.interfaces.IClock

class ChronometerTests {
    @Test
    fun chronometerLifecycleTest() {
        val clockAnswer = object: Answer<Long> {
            var ms = 0L

            override fun answer(invocation: InvocationOnMock?): Long {
                return ms
            }
        }

        val clock = Mockito.mock(IClock::class.java)
        Mockito.`when`(clock.ms).thenAnswer(clockAnswer)
        Mockito.`when`(clock.update()).thenReturn(clock)

        val chronometer: IChronometer = Chronometer(clock)

        assertFailsWith<IllegalStateException> {
            chronometer.update()
        }

        clockAnswer.ms = 500
        chronometer.start()

        assertTrue(chronometer.isRunning)

        chronometer.update()
        assertEquals(0, chronometer.ms)

        clockAnswer.ms = 501

        chronometer.update()
        assertEquals(1, chronometer.ms)

        chronometer.stop()

        assertFalse(chronometer.isRunning)

        assertFailsWith<IllegalStateException> {
            chronometer.update()
        }

        clockAnswer.ms = 600
        chronometer.start()

        assertTrue(chronometer.isRunning)

        chronometer.update()
        assertEquals(1, chronometer.ms)

        clockAnswer.ms = 601

        chronometer.update()
        assertEquals(2, chronometer.ms)

        Mockito.verify(
                clock,
                times(6)
        ).update()
    }
}
