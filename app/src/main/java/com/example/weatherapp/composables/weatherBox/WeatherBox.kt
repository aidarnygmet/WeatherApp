package com.example.weatherapp.composables.weatherBox

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun WeatherBox(temperature: String?, feelsLike: String?, time: String?, id:String?, day: Boolean)
{
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceColorAtElevation(5.dp))
            .padding(16.dp),
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
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "$temperature°C",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Feels like $feelsLike°C",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "$time",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                val newId = id?.toInt()
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
                    if (newId != null) {
                        imageResource = if(newId<511){
                            R.drawable.d10
                        } else if(newId == 511){
                            R.drawable.d13
                        } else{
                            R.drawable.d09
                        }
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
                Image(
                    painter = painterResource(id = imageResource),
                    contentDescription = "Image Content Description", // Provide a content description for accessibility
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

