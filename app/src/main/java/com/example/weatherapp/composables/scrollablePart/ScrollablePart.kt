package com.example.weatherapp.composables.scrollablePart

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.composables.dailyTemperature.DailyTemperatureBox
import com.example.weatherapp.composables.hourlyTemperature.HourlyTemperatureBox
import com.example.weatherapp.data.dailyData
import com.example.weatherapp.data.hourlyData

@Composable
fun ScrollablePart(hourlyData: MutableList<hourlyData>, dailyData: MutableList<dailyData>, sunrise: String, sunset:String, pressure: String, wind:String, uvi:String, humidity:String)
{
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){

        item{
            HourlyTemperatureBox(hourlyData, sunrise, sunset)
            DailyTemperatureBox(dailyData)
        }
        item{
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column{
                    SmallBox(
                        icon = painterResource(id = R.drawable.sunrise),
                        description = "Sunrise",
                        value = sunrise
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    SmallBox(
                        icon = painterResource(id = R.drawable.humidity),
                        description = "Humidity",
                        value = "$humidity%"
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    SmallBox(
                        icon = painterResource(id = R.drawable.sunrise),
                        description = "Wind",
                        value = "$wind m/s"
                    )
                }
                Spacer(modifier = Modifier.width(18.dp))
                Column{
                    SmallBox(
                        icon = painterResource(id = R.drawable.sunset),
                        description = "Sunset",
                        value = sunset
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    SmallBox(
                        icon = painterResource(id = R.drawable.pressure),
                        description = "Pressure",
                        value = pressure
                    )
                    Spacer(modifier = Modifier.height(18.dp))
                    SmallBox(
                        icon = painterResource(id = R.drawable.sun),
                        description = "UVI",
                        value = uvi
                    )
                }
            }
        }
    }
}

@Composable
fun SmallBox(icon: Painter, description: String, value: String)
{
    Log.d("check", "${MaterialTheme.colorScheme.primary.toArgb()}")
    Box(
        modifier = Modifier
            .requiredHeight(180.dp)
            .requiredWidth(180.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = icon,
                contentDescription = null, // Provide a content description for accessibility
                modifier = Modifier.size(48.dp) // Set the size as needed
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                 color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}