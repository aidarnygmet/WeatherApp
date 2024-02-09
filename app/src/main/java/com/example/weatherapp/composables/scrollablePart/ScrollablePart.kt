package com.example.weatherapp.composables.scrollablePart

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import com.example.weatherapp.composables.dailyTemperature.DailyTemperatureBox
import com.example.weatherapp.composables.hourlyTemperature.HourlyTemperatureBox
import com.example.weatherapp.data.dailyData
import com.example.weatherapp.data.hourlyData

@Composable
fun ScrollablePart(hourlyData: MutableList<hourlyData>, dailyData: MutableList<dailyData>, sunrise: String, sunset:String)
{
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ){

        item{
            HourlyTemperatureBox(hourlyData, sunrise, sunset)
            DailyTemperatureBox(dailyData)
        }

    }
}

@Composable
fun SmallBox(icon: Painter, description: String, value: String, modifier: Modifier = Modifier, textColor: Color)
{
    Log.d("check", "${MaterialTheme.colorScheme.primary.toArgb()}")
    Box(
        modifier = modifier
            .fillMaxSize()
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceVariant)
                 ,
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
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
                color = textColor,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                 color = textColor,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}