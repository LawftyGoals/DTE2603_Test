package com.example.oblig1_calendar

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.oblig1_calendar.ui.theme.Oblig1_CalendarTheme
import org.junit.Rule
import org.junit.Test

class CalendarUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val date = "21"
    private val numberOfDays = "202"
    private val month = 6
    private val year = 2029

    @Test
    fun get_number_of_days_since_january_first(){
        composeTestRule.setContent {
            Oblig1_CalendarTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CalendarApp(
                        Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center), month, year)
                }
            }
        }

        composeTestRule.onNodeWithText(date).performClick()
        composeTestRule.onNodeWithText("Det er $numberOfDays dager siden 1. januar.").assertExists("No node with date found.")

    }


}