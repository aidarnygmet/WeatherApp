package com.example.weatherapp.composables.weatherScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.composables.locationSubMenu.LocationMenu
import com.example.weatherapp.composables.scrollablePart.ScrollablePart
import com.example.weatherapp.composables.weatherBox.WeatherBox
import com.example.weatherapp.viewmodel.WeatherDataViewModel

@Composable
fun WeatherScreen(viewModel: WeatherDataViewModel, location: String, navController: NavController)
{
    val weatherDataMap = viewModel.myData.collectAsState().value
    if(weatherDataMap != null){
        var isMenuOpen by remember { mutableStateOf(false) }
        Column(modifier = Modifier
            .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Box(
                    modifier = Modifier
                        .fillMaxHeight(0.8f)
                        .fillMaxWidth(0.2f)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
                        .clickable(onClick = {isMenuOpen = !isMenuOpen}),
                    contentAlignment = Alignment.Center
                ){
                    Icon(Icons.Filled.Menu, contentDescription = "Menu", tint = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier
                    .width(20.dp))
                Box(
                    modifier=Modifier
                        .fillMaxHeight(0.8f)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
                ){
                    Text(text = location, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center)
                }

            }
            Box(modifier = Modifier
                .fillMaxSize()
            ){
                    Column{
                        val weatherData = weatherDataMap[location]
                        WeatherBox(weatherData?.temperature, weatherData?.feelsLike, weatherData?.time, Icons.Default.Check)
                        if (weatherData != null) {
                            ScrollablePart(
                                weatherData.hourlyData,
                                weatherData.dailyData,
                                weatherData.sunrise, weatherData.sunset,
                                weatherData.pressure, weatherData.wind, weatherData.uvi,
                                weatherData.humidity
                            )
                        }
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
                        Modifier, onVisibilityChange = {isMenuOpen = it},navController, viewModel, location
                    )
                }
                }


        }

    }
}