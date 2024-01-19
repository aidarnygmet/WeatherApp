package com.example.weatherapp.navHost

import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weatherapp.composables.mainScreen.MainScreen
import com.example.weatherapp.composables.weatherScreen.WeatherScreen
import com.example.weatherapp.viewmodel.WeatherDataViewModel

@Composable
fun NavHost(viewModel: WeatherDataViewModel){
    Log.d("check", "Launching NavHost")
    val navController = rememberNavController()
    navController.addOnDestinationChangedListener { _, destination, _ ->
        // Your code to handle navigation events here
        Log.d("check", "Navigated to ${destination.route}")
    }
    androidx.navigation.compose.NavHost(navController = navController, startDestination = Screen.MainScreen.route,
        ){

        composable(route= Screen.MainScreen.route,
){
            //Log.d("check", "Launching Main Screen")
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(route= Screen.WeatherScreen.route+"/{location}",
            enterTransition = {slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(700))},
            exitTransition = {
                scaleOut(animationSpec = tween(700))},
            arguments = listOf(
                navArgument("location"){
                    type = NavType.StringType
                }
            )
        ){entry->
            //Log.d("check", "Launching Weather Screen")
            entry.arguments?.getString("location")
                ?.let { WeatherScreen(navController = navController, viewModel = viewModel, location = it) }
        }
    }
}
