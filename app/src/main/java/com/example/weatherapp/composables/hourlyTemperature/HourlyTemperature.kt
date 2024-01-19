package com.example.weatherapp.composables.hourlyTemperature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.hourlyData

@Composable
fun HourlyTemperatureBox(data: List<hourlyData>) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(200.dp)
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        // Using LazyRow for horizontal scrolling
        LazyRow(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            items(24) { index ->
                HourlyTemperature(
                    time = data[index].time,
                    temperature = data[index].temp,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun HourlyTemperature(time: String, temperature: String, modifier: Modifier) {
    // Creating a single text field
    Column(
        modifier = modifier
            .height(90.dp)
            .width(80.dp)
            .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp))
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = time, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
        Text(text = "$temperature°C", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
    }
}
