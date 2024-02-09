package com.example.weatherapp.composables.locationManagerScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.composables.weatherBox.getImageResourcesAndTextColors
import com.example.weatherapp.composables.weatherScreen.dayOrNight
import com.example.weatherapp.data.WeatherData
import com.example.weatherapp.navHost.Screen
import com.example.weatherapp.ui.theme.clearNightScheme
import com.example.weatherapp.viewmodel.WeatherDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LocationManagerScreen(viewModel: WeatherDataViewModel,navController: NavController) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    MaterialTheme(colorScheme = clearNightScheme()){
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
        ) {
            //search bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.1f)
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(.2f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ){
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, Color.Gray.copy(alpha = 0.1f), MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable { navController.popBackStack() }
                        .padding(8.dp),
                        contentAlignment = Alignment.Center){
                        Icon(Icons.Sharp.ArrowBack,
                            contentDescription = "Refresh",
                            modifier = Modifier.fillMaxSize(),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.75f)
                    .clip(MaterialTheme.shapes.medium)
                    .padding(8.dp)
                ){
                    OutlinedTextField(
                        value = text, onValueChange = { text = it },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = Color.Gray.copy(alpha = 0.1f),
                            cursorColor = MaterialTheme.colorScheme.primary,
                            textColor = MaterialTheme.colorScheme.primary,

                        ), label = {
                            Text(text = "Search", style = MaterialTheme.typography.bodyMedium)
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ){
                    var isSearching by remember { mutableStateOf(false) }

                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, Color.Gray.copy(alpha = 0.1f), MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .clickable {
                            keyboardController?.hide()
                            CoroutineScope(Dispatchers.Default).launch {
                                isSearching = true
                                isSearching = !viewModel.addData(text)
                            }

                        }
                        .padding(8.dp),
                        contentAlignment = Alignment.Center){

                        if(isSearching){
                            CircularProgressIndicator(
//                                modifier = Modifier.size(64.dp), // Adjust size as needed
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Icon(Icons.Sharp.Search,
                                contentDescription = "Refresh",
                                modifier = Modifier.fillMaxSize(),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            //location list
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.dp, Color.Gray.copy(alpha = 0.1f), MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primaryContainer)
                        .padding(16.dp)
                ) {
                    viewModel.searchData.collectAsState().value?.forEach {
                        menuItem(
                            it, viewModel, navController
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

//                weatherDataMap?.forEach {
//                    OutlinedTextField(value = it.key, onValueChange = {text = it})
//                }

            }
        }
    }
}

@Composable
fun menuItem(newEntry: Map.Entry<String, WeatherData>, viewModel: WeatherDataViewModel, navController: NavController){
    Log.d("check", "menuItem: ${newEntry.key}")
    var finished by remember{ mutableStateOf(false) }
    val wordsArray = newEntry.key.split("!").toTypedArray()
    var locationName = ""
    var locationState = ""
    var locationCountry = ""
    if(wordsArray.size == 3){
        locationName = wordsArray[0]
        locationState = wordsArray[1]
        locationCountry = wordsArray[2]
    }
    if(finished){
        viewModel.deleteSearchData()
        navController.navigate(Screen.WeatherScreen.withArgs(newEntry.key))
    }
    val result = getImageResourcesAndTextColors(newEntry.value.descr, dayOrNight(
        newEntry.value.time, newEntry.value.sunrise, newEntry.value.sunset
    ))
    val imageResource = result.first
    val imageResource2 = result.second
    val textColor = result.third
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)
        .clip(MaterialTheme.shapes.medium)
        .clickable {
            CoroutineScope(Dispatchers.Default).launch {
                viewModel.updateMap(newEntry)
                finished = true
            }
        }
    ){
        Image(painterResource(id = imageResource2), contentDescription = null
            , modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.5f),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceEvenly
            ){
                Text(text = locationName, style = MaterialTheme.typography.bodyLarge, color = textColor)
                Text(text = "$locationState, $locationCountry", style = MaterialTheme.typography.bodyMedium, color = textColor)
                Text(text = newEntry.value.time, style = MaterialTheme.typography.bodySmall, color = textColor)
            }
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ){
                Text(text = "${newEntry.value.temperature}°C", style = MaterialTheme.typography.displaySmall, color = textColor)
                Spacer(modifier = Modifier.width(16.dp))
                Image(painter = painterResource(id = imageResource), contentDescription = null, modifier = Modifier.size(36.dp))
            }
            
        }
    }

}
