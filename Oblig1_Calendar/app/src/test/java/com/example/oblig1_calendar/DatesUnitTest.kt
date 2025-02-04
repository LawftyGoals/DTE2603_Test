package com.example.oblig1_calendar

import java.util.Calendar
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class DatesUnitTest {

    @Test
    fun workdays_in_month() {
        val calendar = Calendar.getInstance()

        val countWD = countWorkDays(5, 2026, calendar)

        assertEquals(22, countWD)
    }

    @Test
    fun day_of_year() {

        val calendar = Calendar.getInstance()
        val countY = countDayOfYear(30, 5, 2026, calendar)
        assertEquals(181, countY)
    }
}