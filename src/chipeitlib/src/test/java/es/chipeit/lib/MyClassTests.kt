package es.chipeit.lib

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

import es.chipeit.lib.core.Timer
import es.chipeit.lib.core.TimerClockDivider
import es.chipeit.lib.interfaces.hzToMs
import es.chipeit.lib.interfaces.IClock
import es.chipeit.lib.interfaces.msToHz
import es.chipeit.lib.io.ISwitchObserver

class MyClassTests {
    @Test
    fun hzToMsTest() {
        assertEquals(17, hzToMs(58)) // 17.24
        assertEquals(17, hzToMs(60)) // 16.67
        assertEquals(16, hzToMs(62)) // 16.13

        assertFailsWith<IllegalArgumentException> { hzToMs(-60) }
        assertFailsWith<IllegalArgumentException> { hzToMs(0) }
    }

    @Test
    fun msToHzTest() {
        /* In case of X.5, is rounded to the nearest even. */
        assertEquals(67, msToHz(15)) // 66.67
        assertEquals(62, msToHz(16)) // 62.50
        assertEquals(59, msToHz(17)) // 58.82

        assertFailsWith<IllegalArgumentException> { msToHz(-60) }
        assertFailsWith<IllegalArgumentException> { msToHz(0) }
    }
}
