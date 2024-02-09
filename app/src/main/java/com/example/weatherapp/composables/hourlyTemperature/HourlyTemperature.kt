package com.example.weatherapp.composables.hourlyTemperature

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.composables.weatherBox.getImageResourcesAndTextColors
import com.example.weatherapp.composables.weatherScreen.dayOrNight
import com.example.weatherapp.data.hourlyData

@Composable
fun HourlyTemperatureBox(data: List<hourlyData>, sunrise: String, sunset:String) {
    val secondaryColor = MaterialTheme.colorScheme.primaryContainer
    val tertiaryColor = MaterialTheme.colorScheme.secondaryContainer
    var itemToShow by remember{ mutableIntStateOf(0) }
    var tempButtonColor by remember { mutableStateOf(tertiaryColor) }
    var humButtonColor by remember { mutableStateOf(secondaryColor) }
    var windButtonColor by remember { mutableStateOf(secondaryColor) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(250.dp)
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(2.dp, Color.Gray.copy(alpha = 0.1f), MaterialTheme.shapes.medium)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column{
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        )
        {
            Box(modifier = Modifier
                .fillMaxWidth(.33f)
                .height(80.dp)
                .padding(vertical = 16.dp, horizontal = 4.dp)
            ){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
                    .background(tempButtonColor)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.shapes.medium
                    )
                    .clickable {
                        if (itemToShow != 0) {
                            itemToShow = 0
                            tempButtonColor = tertiaryColor
                            humButtonColor = secondaryColor
                            windButtonColor = secondaryColor
                        }
                    }
                    .padding(8.dp),
                    contentAlignment = Alignment.Center
                ){
    //                Text(
    //                    text = "Temperature, °C",
    //                    style = MaterialTheme.typography.bodyMedium,
    //                    color = MaterialTheme.colorScheme.secondary
    //                )
                    Image(painter = painterResource(id = R.drawable.temp), contentDescription = "", modifier = Modifier.size(36.dp))
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth(.5f)
                .height(80.dp)
                .padding(vertical = 16.dp, horizontal = 4.dp)
            ){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
                    .background(humButtonColor)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.shapes.medium
                    )
                    .clickable {
                        if (itemToShow != 1) {
                            itemToShow = 1
                            tempButtonColor = secondaryColor
                            humButtonColor = tertiaryColor
                            windButtonColor = secondaryColor
                        }
                    }
                    .padding(8.dp),
                    contentAlignment = Alignment.Center
                ){
                    Image(painter = painterResource(id = R.drawable.humidity), contentDescription = "", modifier = Modifier.size(36.dp))
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(vertical = 16.dp, horizontal = 4.dp)
            ){
                Box(modifier = Modifier
                    .fillMaxSize()
                    .clip(MaterialTheme.shapes.medium)
                    .background(windButtonColor)
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.shapes.medium
                    )
                    .clickable {
                        if (itemToShow != 2) {
                            itemToShow = 2
                            tempButtonColor = secondaryColor
                            humButtonColor = secondaryColor
                            windButtonColor = tertiaryColor
                        }
                    }
                    .padding(8.dp),
                    contentAlignment = Alignment.Center
                ){
    //                Text(
    //                    text = "Wind, m/s",
    //                    style = MaterialTheme.typography.bodyMedium,
    //                    color = MaterialTheme.colorScheme.secondary
    //                )
                    Image(painter = painterResource(id = R.drawable.wind), contentDescription = "", modifier = Modifier.size(36.dp))
                }
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            items(24) { index ->
                when (itemToShow) {
                    0 -> {
                        HourlyTemperature(
                            time = data[index].time,
                            temperature = data[index].temp,
                            modifier = Modifier,
                            id = data[index].descr,
                            day = dayOrNight(data[index].time, sunrise, sunset),
                            0
                        )
                    }
                    1 -> {
                        HourlyTemperature(
                            time = data[index].time,
                            temperature = data[index].humidity,
                            modifier = Modifier,
                            id = "-1",
                            day = dayOrNight(data[index].time, sunrise, sunset),
                            1
                        )
                    }
                    else -> {
                        HourlyTemperature(
                            time = data[index].time,
                            temperature = data[index].wind,
                            modifier = Modifier,
                            id = "-1",
                            day = dayOrNight(data[index].time, sunrise, sunset),
                            2
                        )
                    }
                }

            }
        }
        }
    }
}

@Composable
fun HourlyTemperature(time: String, temperature: String, modifier: Modifier, id:String, day:Boolean, itemToShow: Int) {
    val newId = id.toInt()
    Log.d("check", "id: $newId")
    val imageResource : Int = when (itemToShow) {
        0 -> {
            getImageResourcesAndTextColors(id, day).first
        }
        1 -> {
            R.drawable.ic_launcher_foreground
        }
        else -> {
            R.drawable.ic_launcher_foreground
        }
    }
    // Creating a single text field
    Column(
        modifier = modifier
            .width(64.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = time, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = "Weather Icon",
            modifier = modifier.size(48.dp)
        )
        when (itemToShow) {
            0 -> {
                Text(text = temperature, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)}
            1 -> {
                Text(text = temperature, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            }
            else -> {
                Text(text = temperature, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
