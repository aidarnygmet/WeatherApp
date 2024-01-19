package com.example.weatherapp.composables.dailyTemperature

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.weatherapp.data.dailyData

@Composable
fun DailyTemperatureBox(data: List<dailyData>){
    val expandedIndices = remember { mutableStateListOf<Int>() }
    Box (
        modifier = Modifier

            .fillMaxSize()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ){
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            for (index in 0 .. 7) {
                DailyTemperature(
                    data = data[index],
                    expandedIndices = expandedIndices,
                    index = index,
                    onClick = {
                        if (expandedIndices.contains(index)) {
                            expandedIndices.remove(index)
                        } else {
                            expandedIndices.add(index)
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun DailyTemperature(data: dailyData, index: Int, expandedIndices: List<Int>, onClick: () -> Unit){
    val expandedHeight by animateDpAsState(
        targetValue = if (expandedIndices.contains(index)) 450.dp else 80.dp,
        animationSpec = tween(durationMillis = 300), label = "expansionHeight"
    )
    Box(modifier = Modifier
        .height(expandedHeight)
        .fillMaxWidth()
        .padding(8.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(color = MaterialTheme.colorScheme.secondaryContainer)
        .clickable(onClick = onClick)
        //.zIndex(if (expandedIndices.contains(index)) 1f else 0f)
        //.animateContentSize()
        ,
        contentAlignment = Alignment.Center

    ){
        Column {
            AnimatedVisibility(
                visible = !expandedIndices.contains(index),
                enter = fadeIn(),
                exit = fadeOut()
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = data.day, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "${data.tempDay}°C", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "${data.tempNight}°C", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.weight(1f))
                }
            }


            AnimatedVisibility(
                visible = expandedIndices.contains(index),
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = data.day, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "${data.tempDay}°C", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(text = "${data.tempNight}°C", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.secondary)
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(8.dp)
                        ){
                        Text(text = data.summary, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSecondary)
                        }
                    Spacer(modifier = Modifier.weight(1f))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        MiniBox(icon = Icons.Default.Call, description = "Sunrise", value = data.sunrise)
                        Spacer(modifier = Modifier.weight(1f))
                        MiniBox(icon = Icons.Default.Call, description = "Sunset", value = data.sunset)
                        Spacer(modifier = Modifier.weight(1f))

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        MiniBox(icon = Icons.Default.Call, description = "Humidity", value = "${data.humidity}%")
                        Spacer(modifier = Modifier.weight(1f))
                        MiniBox(icon = Icons.Default.Call, description = "Wind", value = "${data.wind}m/s")
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

    }
}
@Composable
fun MiniBox(icon: ImageVector, description: String, value: String)
{
    Box(
        modifier = Modifier
            .height(130.dp)
            .width(130.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(color = MaterialTheme.colorScheme.secondary)

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
