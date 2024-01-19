package com.example.weatherapp.composables.mainScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.navHost.Screen
import com.example.weatherapp.viewmodel.WeatherDataViewModel

@Composable
fun MainScreen(navController: NavController, viewModel: WeatherDataViewModel) {
    val loc = viewModel.locationState.collectAsState().value
    val locationName = viewModel.myData.collectAsState().value

    if (loc != null) {
        LaunchedEffect(Unit){
            Log.d("check","${loc.latitude}")
            viewModel.setLocation(loc.latitude.toString(), loc.longitude.toString())
            viewModel.fetchData()
        }

    } else {
        Text("Retrieving location...")
    }
    if (locationName!=null) {
        LaunchedEffect(Unit ){
            Log.d("check","locationName: ${locationName.keys.toList()[0]}")
            navController.navigate(Screen.WeatherScreen.withArgs(locationName.keys.toList()[0]))
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()
        , contentAlignment = Alignment.Center){
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp), // Adjust size as needed
                color = Color(0xFF33283F)
            )
        }
    }

}