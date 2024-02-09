package com.example.weatherapp.composables.weatherScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.composables.locationSubMenu.LocationMenu
import com.example.weatherapp.composables.scrollablePart.ScrollablePart
import com.example.weatherapp.composables.weatherBox.WeatherBox
import com.example.weatherapp.ui.theme.clearDayScheme
import com.example.weatherapp.ui.theme.clearNightScheme
import com.example.weatherapp.ui.theme.greyDayScheme
import com.example.weatherapp.ui.theme.greyNightScheme
import com.example.weatherapp.viewmodel.WeatherDataViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun WeatherScreen(viewModel: WeatherDataViewModel, location: String, navController: NavController)
{
    val weatherDataMap = viewModel.myData.collectAsState().value
    val currentTheme: ColorScheme
    if(weatherDataMap != null) {
        val weatherData = weatherDataMap[location]!!
        val dayOrNight = dayOrNight(weatherData.time, weatherData.sunrise, weatherData.sunset)
        currentTheme = if(weatherData.descr == "800" ||weatherData.descr == "801"){
            if(dayOrNight){
                clearDayScheme()
            } else {
                clearNightScheme()
            }
        } else {
            if(dayOrNight){
                greyDayScheme()
            } else {
                greyNightScheme()
            }
        }

        var isMenuOpen by remember { mutableStateOf(false) }
        MaterialTheme(colorScheme = currentTheme,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    Color.Gray.copy(alpha = 0.1f),
                                    MaterialTheme.shapes.medium
                                )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .fillMaxWidth(0.2f)
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                                    .clickable(onClick = { isMenuOpen = !isMenuOpen }),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Menu",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        Spacer(
                            modifier = Modifier
                                .width(20.dp)
                        )
                        Box(
                            modifier = Modifier
                                .border(2.dp, Color.Gray.copy(alpha = .1f), MaterialTheme.shapes.medium)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(0.8f)
                                    .fillMaxWidth()
                                    .clip(MaterialTheme.shapes.medium)
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            ) {
                                Text(
                                    text = location.split("!").toTypedArray()[0],
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.Center),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Column {


                            WeatherBox(
                                weatherData,
                                weatherData.descr,
                                dayOrNight(weatherData.time, weatherData.sunrise, weatherData.sunset)
                            )
                            ScrollablePart(
                                weatherData.hourlyData,
                                weatherData.dailyData,
                                weatherData.sunrise, weatherData.sunset
                            )
                        }
                        this@Column.AnimatedVisibility(
                            visible = isMenuOpen,
                            enter = slideInHorizontally(
                                initialOffsetX = { -it }),
                            exit = slideOutHorizontally(
                                targetOffsetX = { -it })
                        ) {
                            LocationMenu(
                                modifier =
                                Modifier,
                                onVisibilityChange = { isMenuOpen = it },
                                navController,
                                viewModel,
                                location
                            )
                        }
                    }


                }


            }
        }
    }
}


fun dayOrNight(time1: String, time2: String, time3: String): Boolean {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")

    val localTime1 = LocalTime.parse(time1, formatter)
    val localTime2 = LocalTime.parse(time2, formatter)
    val localTime3 = LocalTime.parse(time3, formatter)
    return localTime1.isAfter(localTime2) && localTime1.isBefore(localTime3)
}