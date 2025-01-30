package com.example.oblig1_calendar

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.oblig1_calendar.ui.theme.Oblig1_CalendarTheme

import java.util.Calendar.DATE
import java.util.Calendar.DAY_OF_WEEK
import java.util.Calendar.DAY_OF_YEAR
import java.util.Calendar.MONTH

import java.util.Calendar.WEEK_OF_YEAR
import java.util.Calendar.YEAR

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Oblig1_CalendarTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CalendarApp(Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center))
                }

            }
        }
    }
}

@Composable
fun CalendarBody(modifier: Modifier = Modifier, month: Int, year: Int) {
    val bgImage = painterResource(R.drawable.cat_with_shades_bg)

    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar.set(year, month, 1)

    val firstWeekNumber = calendar.get(WEEK_OF_YEAR)
    val numberOfDaysInMonth = getNumberOfDaysInMonth(month, year)
    val dayOfWeek = calendar.get(DAY_OF_WEEK)

    val dates = addPaddingToDates(numberOfDaysInMonth, dayOfWeek)

    Box(modifier.fillMaxWidth()){

        Image(modifier = Modifier.fillMaxWidth(), contentScale = ContentScale.FillWidth  , painter = bgImage, contentDescription = null, alpha= 0.5f)

        Column (horizontalAlignment = Alignment.CenterHorizontally, modifier=Modifier.fillMaxWidth()){

            CalendarHeader(calendar = calendar)
            DateRow()
            WeekNumbersAndDates(firstWeekNumber, dates, month, year)
            CalendarFooter(month = month, year = year)

        }


    }
}

@Composable
fun WeekNumbersAndDates(firstWeekNumber: Int, dates: List<String>, month: Int, year: Int){

    LazyVerticalGrid(GridCells.Fixed(8), horizontalArrangement = Arrangement.Absolute.SpaceBetween){

        item {
            InfoContainer(firstWeekNumber.toString())
        }

        items(7){
            DateContainer(dates[it], it, month, year)
        }

        item { InfoContainer((firstWeekNumber + 1).toString())}

        items(7 ){
            DateContainer(dates[it + 7], it + 7, month, year)
        }

        item { InfoContainer((firstWeekNumber + 2).toString())}

        items(7 ){
            DateContainer(dates[it + 14], it + 14, month, year)
        }

        item { InfoContainer((firstWeekNumber + 3).toString())}

        items(7 ){
            DateContainer(dates[it + 21], it + 21, month, year)
        }

        if(dates.count() > 28){

            item { InfoContainer((firstWeekNumber + 4).toString())}

            items(7 ){
                DateContainer(dates[it + 28], it + 28, month, year)
            }
        }

        if(dates.count() > 35){

            item { InfoContainer((firstWeekNumber + 5).toString())}

            items(7 ){
                DateContainer(dates[it + 35], it + 35, month, year)
            }
        }



    }

}

@Composable
fun CalendarHeader(modifier: Modifier = Modifier, calendar: Calendar){

    val months = listOf(R.string.January, R.string.February, R.string.March, R.string.April, R.string.May, R.string.June, R.string.July, R.string.August, R.string.September, R.string.October, R.string.November, R.string.December)

    val month = calendar.get(Calendar.MONTH)
    val year = calendar.get(Calendar.YEAR)

    Column(modifier
        .fillMaxWidth()
        .border(width = 2.dp, color = Color.Black)
        .background(color = Color(1f, 1f, 1f, 0.8f))
        .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "${stringResource(months[month])} $year", fontSize = 18.sp)
    }

}

@Composable
fun CalendarFooter(modifier: Modifier = Modifier, month: Int, year: Int){

    val numberOfDays = getNumberOfDaysInMonth(month, year)
    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar.set(year, month, 1)

    var workDays = 0

    for(date in 1..numberOfDays){
        calendar.set(DATE, date)
        val dayOfWeek = calendar.get(DAY_OF_WEEK)
        if(dayOfWeek != 1 && dayOfWeek != 7){
            workDays++
        }
    }

    Column(modifier
        .fillMaxWidth()
        .border(width = 2.dp, color = Color.Black)
        .background(color = Color(1f, 1f, 1f, 0.8f))
        .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Denne måneden har $workDays arbeidsdager.", fontSize = 12.sp)
        Text(text = "Klikk på en dag for å finne antall dager siden 1. januar.")
    }

}

@Composable
fun DateRow(){
    val dateNames = listOf("","M", "T", "W", "T", "F", "S", "S")

    LazyVerticalGrid(GridCells.Fixed(8)) {
        items(8){
            InfoContainer(dateNames[it])
        }
    }
}

@Composable
fun InfoContainer(text: String){
    Row(Modifier
        .border(width = 1.dp, color = Color.Black)
        .background(color = typeCellCheck(text))
        .fillMaxSize(), horizontalArrangement = Arrangement.Center){
            Text( text = text)

    }


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateContainer(text: String, date: Int, month: Int, year: Int){

    val calendar = Calendar.getInstance()
    calendar.clear()
    calendar.set(year, month, date)
    val dayOfYear = calendar.get(DAY_OF_YEAR)

    var showDialogue by remember { mutableStateOf(false) }



    Row(Modifier
        .fillMaxSize()
        .border(width = 1.dp, color = Color.Black)
        .background(color = typeCellCheck(text)), horizontalArrangement = Arrangement.Center){
        TextButton(onClick = {showDialogue = true}) {
            Text( text = text)

        }
    }

    if(showDialogue){
        BasicAlertDialog(onDismissRequest = {showDialogue = false}) {
            Column(Modifier.background(Color.White)) {
                Text(text="Det er $dayOfYear siden 1. januar.")
                Button(onClick = { showDialogue = false }) {
                    Text("Close")
                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun CalendarApp(modifier: Modifier = Modifier, month: Int = 5, year: Int = 2026) {

    CalendarBody(modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center), month, year)

}


fun typeCellCheck(text: String): Color{
    if(text == ""){
        return Color(0.2f,0.2f,0.7f,0.1f)
    }
    if(text.toIntOrNull() != null) {
        return Color(0.2f, 0.2f, 0.7f, 0.3f)
    }
    return Color(0.7f, 0.2f, 0.2f, 0.3f)
}

fun getNumberOfDaysInMonth(month: Int, year: Int): Int{
    val tempCalendar = Calendar.getInstance()
    tempCalendar.clear()
    tempCalendar.set(year, month, 1)
    tempCalendar.add(Calendar.MONTH, 1)
    tempCalendar.add(Calendar.DATE, -1)

    return tempCalendar.get(DATE)
}

fun getNumberOfWeeks(month: Int, year: Int): Int{
    val tempCalendar = Calendar.getInstance()
    tempCalendar.clear()
    tempCalendar.set(year, month, 1)
    val firstWeek = tempCalendar.get(WEEK_OF_YEAR)

    tempCalendar.add(Calendar.MONTH, 1)
    tempCalendar.add(Calendar.DATE, -1)
    val lastWeek = tempCalendar.get(WEEK_OF_YEAR)

    return lastWeek - firstWeek
}

fun addPaddingToDates(numberOfDaysInMonth: Int, dayOfWeek: Int  ): List<String>{

    val adjustedDay = when(dayOfWeek){
        1 -> 6
        2 -> 0
        3 -> 1
        4 -> 2
        5 -> 3
        6 -> 4
        else -> 5
    }

    var dates = (1..numberOfDaysInMonth).toList().map {date -> date.toString()}

    if( adjustedDay > 0){
        dates = List(adjustedDay) {""} + dates
    }

    val total = dates.count()

    if(total > 28){
        dates = dates + List(if (total > 35) 42 else 35 - total) {""}
    }

    return dates

}