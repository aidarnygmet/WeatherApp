package com.example.weatherapp.navHost

sealed class Screen(val route: String){
    object MainScreen: Screen("mainScreen")
    object WeatherScreen: Screen("weatherScreen")
    fun withArgs(vararg args: String): String{
        return buildString{
            append(route)
            args.forEach {
                append("/$it")
            }
        }
    }
}

