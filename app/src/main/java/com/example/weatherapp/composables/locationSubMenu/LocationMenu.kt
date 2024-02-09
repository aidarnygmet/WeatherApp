package com.example.weatherapp.composables.locationSubMenu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.composables.weatherBox.getImageResourcesAndTextColors
import com.example.weatherapp.composables.weatherScreen.dayOrNight
import com.example.weatherapp.navHost.Screen
import com.example.weatherapp.viewmodel.WeatherDataViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LocationMenu(
    modifier: Modifier,
    onVisibilityChange: (Boolean) -> Unit,
    navController: NavController,
    viewModel: WeatherDataViewModel,
    currentLocation: String,
) {

    val data = viewModel.myData.collectAsState().value

    Column(modifier = Modifier
        .fillMaxWidth(0.8f)
        .fillMaxHeight()
        .padding(8.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.primaryContainer))

    {
        if (data != null) {
            for (locationKey in data.keys.toList()) {
                val buttonColor  = if(locationKey == currentLocation){
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.secondaryContainer
                }
                val res = getImageResourcesAndTextColors(data[locationKey]!!.descr, dayOrNight(
                    data[locationKey]!!.time, data[locationKey]!!.sunrise, data[locationKey]!!.sunset
                ))
                val imageResource = res.second
                val textColor = res.third
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(8.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .clickable { if(locationKey != currentLocation){
                            onVisibilityChange(false)
                            navController.navigate(Screen.WeatherScreen.withArgs(locationKey))
                        } },
                    contentAlignment = Alignment.Center
                ){
                    Image(
                        painterResource(id = imageResource), contentDescription = null
                        , modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
                    Text(locationKey.split("!").toTypedArray()[0]
                        , textAlign = TextAlign.Center, color = textColor)
                }
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth(0.3f)
                .height(50.dp)
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .clickable {
                    navController.navigate(Screen.LocationManagerScreen.route)
                },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
        }


    }
}
