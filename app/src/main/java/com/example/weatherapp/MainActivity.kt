package com.example.weatherapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.weatherapp.navHost.NavHost
import com.example.weatherapp.viewmodel.WeatherDataViewModel
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val weatherDataViewModel = WeatherDataViewModel(LocationServices.getFusedLocationProviderClient(this))
        setContent {
            NavHost(viewModel = weatherDataViewModel)
        }
    }
}















