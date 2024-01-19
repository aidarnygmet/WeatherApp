package com.example.weatherapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.weatherapp.navHost.NavHost
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.example.weatherapp.viewmodel.WeatherDataViewModel
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherDataViewModel = WeatherDataViewModel(LocationServices.getFusedLocationProviderClient(this))
        setContent {
            WeatherAppTheme {
                Surface(color = MaterialTheme.colorScheme.surface) {
                    NavHost(viewModel = weatherDataViewModel)
                }

            }
        }
    }
}















