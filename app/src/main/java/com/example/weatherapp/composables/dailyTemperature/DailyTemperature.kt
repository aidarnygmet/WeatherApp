package com.example.weatherapp.composables.dailyTemperature

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.data.dailyData

@Composable
fun DailyTemperatureBox(data: List<dailyData>){
    val expandedIndices = remember { mutableStateListOf<Int>() }
    Box (
        modifier = Modifier

            .fillMaxSize()
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.1f), MaterialTheme.shapes.medium)
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
    val color = if(expandedIndices.contains(index))MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer
    Box(modifier = Modifier
        .height(expandedHeight)
        .fillMaxWidth()
        .padding(8.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(color = color)
        .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.1f), MaterialTheme.shapes.medium)
        .clickable(onClick = onClick)
        //.zIndex(if (expandedIndices.contains(index)) 1f else 0f)
        //.animateContentSize()
        ,
        contentAlignment = Alignment.Center

    ){
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


            AnimatedVisibility(
                visible = expandedIndices.contains(index)
            ) {
                Column {
                    Box(modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(8.dp)
                        ){
                        Text(text = data.summary, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.tertiary)
                        }
                    Spacer(modifier = Modifier.weight(1f))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        MiniBox(icon = painterResource(id = R.drawable.sunrise), description = "Sunrise", value = data.sunrise)
                        Spacer(modifier = Modifier.weight(1f))
                        MiniBox(icon = painterResource(id = R.drawable.sunset), description = "Sunset", value = data.sunset)
                        Spacer(modifier = Modifier.weight(1f))

                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        MiniBox(icon = painterResource(id = R.drawable.humidity), description = "Humidity", value = "${data.humidity}%")
                        Spacer(modifier = Modifier.weight(1f))
                        MiniBox(icon = painterResource(id = R.drawable.pressure), description = "Wind", value = "${data.wind}m/s")
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

    }
}
@Composable
fun MiniBox(icon: Painter, description: String, value: String)
{
    Box(
        modifier = Modifier
            .height(130.dp)
            .width(130.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha=0.1f), MaterialTheme.shapes.medium)

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
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
