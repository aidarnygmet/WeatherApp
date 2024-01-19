package com.example.weatherapp.composables.hourlyTemperature

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.composables.weatherScreen.dayOrNight
import com.example.weatherapp.data.hourlyData

@Composable
fun HourlyTemperatureBox(data: List<hourlyData>, sunrise: String, sunset:String) {
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
                Log.d("time", "time: ${data[index].time} sunrise: $sunrise sunset: $sunset isDayorNight ${dayOrNight(data[index].time, sunrise, sunset)} description: ${data[index].descr}")
                HourlyTemperature(
                    time = data[index].time,
                    temperature = data[index].temp,
                    modifier = Modifier,
                    id = data[index].descr,
                    day = dayOrNight(data[index].time, sunrise, sunset)
                )
            }
        }
    }
}

@Composable
fun HourlyTemperature(time: String, temperature: String, modifier: Modifier, id:String, day:Boolean) {
    val newId = id.toInt()
    var imageResource = 0
    Log.d("check", "id: $newId")
    if(newId in 200..232){
        imageResource = R.drawable.d01
    }
    else if(newId in 300..321){
        imageResource = R.drawable.d09
    }
    else if(newId in 500..531)
    {
        imageResource = if(newId<511){
            R.drawable.d10
        } else if(newId == 511){
            R.drawable.d13
        } else{
            R.drawable.d09
        }
    }
    else if(newId in 600..622){
        imageResource = R.drawable.d13
    }
    else if(newId in 701..781){
        imageResource = R.drawable.d50
    }
    else if(newId==800){
        if(day){
            imageResource = R.drawable.d01}
        else{
            imageResource = R.drawable.n01}
    }
    else if(newId == 801) {
        if(day){
            imageResource = R.drawable.d02}
        else{
            imageResource = R.drawable.n02}
    }
    else if(newId == 802) {
        imageResource = R.drawable.d03
    }
    else if(newId == 803 || newId == 804) {
        imageResource = R.drawable.d04
    }
    else {
        Text(
            text = "Unknown",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
    // Creating a single text field
    Column(
        modifier = modifier
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = time, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Weather Icon",
            modifier = modifier.size(64.dp)
        )
        Text(text = "$temperature°C", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
    }
}
