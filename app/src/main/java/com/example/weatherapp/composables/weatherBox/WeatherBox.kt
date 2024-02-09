package com.example.weatherapp.composables.weatherBox


import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.composables.scrollablePart.SmallBox
import com.example.weatherapp.data.WeatherData


@SuppressLint("SuspiciousIndentation")
@Composable
fun WeatherBox(weatherData: WeatherData, id:String?, day: Boolean)
{
    val columnHeight = 200.dp
    var expanded by remember { androidx.compose.runtime.mutableStateOf(false) }
    val expandedHeight by animateDpAsState(
        targetValue = if (expanded) 800.dp else 200.dp,
        animationSpec = tween(durationMillis = 300), label = "expansionHeight"
    )
    val imageResourcesAndTextColors = getImageResourcesAndTextColors(id, day)

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(expandedHeight)
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .border(2.dp, Color.Gray.copy(alpha = 0.1f), MaterialTheme.shapes.medium)
            .clickable { expanded = !expanded }

            )

        {
            Image(painter= painterResource(id = imageResourcesAndTextColors.second),contentDescription = "Background image",
                modifier = Modifier
                    .fillMaxSize()
                    ,
                contentScale = ContentScale.Crop
            )
            Column {
                Box(
                    modifier = Modifier
                        .height(columnHeight)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center,

                    ) {

                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        // Left half of the box - Temperature
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight()

                            ,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "${weatherData.temperature}°C",
                                style = MaterialTheme.typography.displaySmall,
                                color = imageResourcesAndTextColors.third
                            )
                            Text(
                                text = "Feels like ${weatherData.feelsLike}°C",
                                style = MaterialTheme.typography.bodySmall,
                                color = imageResourcesAndTextColors.third
                            )
                            Text(
                                text = weatherData.time,
                                style = MaterialTheme.typography.bodySmall,
                                color = imageResourcesAndTextColors.third
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {


                            Image(
                                painter = painterResource(id = imageResourcesAndTextColors.first),
                                contentDescription = "Image Content Description", // Provide a content description for accessibility
                                modifier = Modifier.size(100.dp)
                            )
                        }
                    }
                }
                AnimatedVisibility(visible = expanded, modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(.5f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SmallBox(
                                icon = painterResource(id = R.drawable.sunrise),
                                description = "Sunrise",
                                value = weatherData.sunrise, modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .aspectRatio(1f),
                                textColor = imageResourcesAndTextColors.third
                            )
                            Spacer(modifier = Modifier.weight(.1f))
                            SmallBox(
                                icon = painterResource(id = R.drawable.humidity),
                                description = "Humidity",
                                value = "${weatherData.humidity}%", modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .aspectRatio(1f),
                                textColor = imageResourcesAndTextColors.third
                            )
                            Spacer(modifier = Modifier.weight(.1f))
                            SmallBox(
                                icon = painterResource(id = R.drawable.sunrise),
                                description = "Wind",
                                value = "${weatherData.wind} m/s", modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .aspectRatio(1f),
                                textColor = imageResourcesAndTextColors.third
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            SmallBox(
                                icon = painterResource(id = R.drawable.sunset),
                                description = "Sunset",
                                value = weatherData.sunset, modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .aspectRatio(1f),
                                textColor = imageResourcesAndTextColors.third
                            )
                            Spacer(modifier = Modifier.weight(.1f))
                            SmallBox(
                                icon = painterResource(id = R.drawable.pressure),
                                description = "Pressure",
                                value = weatherData.pressure, modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .aspectRatio(1f),
                                textColor = imageResourcesAndTextColors.third
                            )
                            Spacer(modifier = Modifier.weight(.1f))
                            SmallBox(
                                icon = painterResource(id = R.drawable.d01),
                                description = "UVI",
                                value = weatherData.uvi, modifier = Modifier
                                    .fillMaxWidth(.8f)
                                    .aspectRatio(1f),
                                textColor = imageResourcesAndTextColors.third
                            )
                        }
                    }
                }
            }
        }


}

fun getImageResourcesAndTextColors(id: String?, day: Boolean): Triple<Int, Int, Color> {
    var textColor = Color(0xFF0A1C2B)
    val newId = id?.toInt()
    var imageResource = 0
    var imageResource2 = 0
    if (newId in 200..232) {
        imageResource = R.drawable.d01
        imageResource2 = R.drawable.clear_day
        textColor = Color(0xFF0A1C2B)
    } else if (newId in 300..321) {
        imageResource = R.drawable.d09
        imageResource2 = R.drawable.cloudy_day
        textColor = Color(0xFF0A1C2B)
    } else if (newId in 500..531) {
        if (newId != null) {
            if (newId < 511) {
                imageResource = R.drawable.d10
                imageResource2 = R.drawable.rain_day
                textColor = Color(0xFF0A1C2B)
            } else if (newId == 511) {
                imageResource =  R.drawable.d13
                textColor = Color(0xFF0A1C2B)
                if(day){
                    imageResource2 = R.drawable.snow_day
                } else {
                    imageResource2 = R.drawable.snow_night
                }
            } else {
                imageResource = R.drawable.d09
                imageResource2 = R.drawable.rain_night
            }
        }
    } else if (newId in 600..622) {
        imageResource = R.drawable.d13
        textColor = Color(0xFF0A1C2B)
        if(day){
            imageResource2 = R.drawable.snow_day
        } else {
            imageResource2 = R.drawable.snow_night
        }
    } else if (newId in 701..781) {
        textColor = Color(0xFF0A1C2B)
        imageResource = R.drawable.d50
        imageResource2 = R.drawable.fog
    } else if (newId == 800) {
        if (day) {
            textColor = Color(0xFF0A1C2B)
            imageResource = R.drawable.d01
            imageResource2 = R.drawable.clear_day
        } else {
            textColor = Color(0xFFECEFF2)
            imageResource = R.drawable.n01
            imageResource2 = R.drawable.clear_night
        }
    } else if (newId == 801) {
        if (day) {
            textColor = Color(0xFF0A1C2B)
            imageResource = R.drawable.d02
            imageResource2 = R.drawable.few_cloudy_day
        } else {
            textColor = Color(0xFFECEFF2)
            imageResource = R.drawable.n02
            imageResource2 = R.drawable.clear_night
        }
    } else if (newId == 802) {
        imageResource = R.drawable.d03
        if(day){
            textColor = Color(0xFF0A1C2B)
            imageResource2 = R.drawable.cloudy_day
        } else {
            textColor = Color(0xFFECEFF2)
            imageResource2 = R.drawable.cloudy_night
        }
    } else if (newId == 803 || newId == 804) {
        imageResource = R.drawable.d04
        if(day){
            textColor = Color(0xFF0A1C2B)
            imageResource2 = R.drawable.cloudy_day
        } else {
            textColor = Color(0xFFECEFF2)
            imageResource2 = R.drawable.cloudy_night
        }
    } else {
        textColor = Color(0xFF0A1C2B)
        imageResource = R.drawable.ic_launcher_foreground
        imageResource2 = R.drawable.ic_launcher_foreground
    }
    return Triple(imageResource, imageResource2, textColor)
}

