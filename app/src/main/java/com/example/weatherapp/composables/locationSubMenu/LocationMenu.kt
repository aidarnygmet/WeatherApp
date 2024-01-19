package com.example.weatherapp.composables.locationSubMenu

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.surfaceColorAtElevation
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weatherapp.navHost.Screen
import com.example.weatherapp.viewmodel.WeatherDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LocationMenu(
    modifier: Modifier,
    onVisibilityChange: (Boolean) -> Unit,
    navController: NavController,
    viewModel: WeatherDataViewModel,
    currentLocation: String
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val data = viewModel.myData.collectAsState().value
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxWidth(0.8f)
        .fillMaxHeight()
        .padding(8.dp)
        .clip(MaterialTheme.shapes.medium)
        .background(MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)))

    {
        if (data != null) {
            for (locationKey in data.keys.toList()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(8.dp)
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.secondary)
                        .clickable { if(locationKey != currentLocation){
                            onVisibilityChange(false)
                            navController.navigate(Screen.WeatherScreen.withArgs(locationKey))
                        } },
                    contentAlignment = Alignment.Center
                ){
                    Text(locationKey
                        , textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
        OutlinedTextField(
            value = text,
            onValueChange = {text = it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        if (keyboardController != null) {
            AddButton(keyboardController = keyboardController, viewModel = viewModel, text = text,
                onChange = {
                    text=""
                })
        }
//        Button(
//            onClick={
//                keyboardController?.hide()
//                CoroutineScope(Dispatchers.Main).launch {
//                    viewModel.addData(text)
//                    text=""
//                }
//            }, modifier = Modifier
//                .padding(8.dp)
//                .clip(MaterialTheme.shapes.medium)
//                .background(MaterialTheme.colorScheme.primary)
//        ){
//            Icon(Icons.Filled.Add, contentDescription = "Menu")
//        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddButton(keyboardController: SoftwareKeyboardController, viewModel: WeatherDataViewModel, text:String,
              onChange: () -> Unit)
{

    var isLoading by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Log.d("check", "AddButton: $text is loading: $isLoading")
    Row (
        modifier = Modifier
            .fillMaxWidth(0.3f)
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.secondary, MaterialTheme.shapes.medium)
            .clickable {
                if (!isLoading) {
                    keyboardController?.hide()
                    CoroutineScope(Dispatchers.Default).launch {
                        isLoading = true
                        isLoading = !viewModel.addData(text)
                    }

                }
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        if (isLoading) {
            finished = true
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp), // Adjust size as needed
                color = MaterialTheme.colorScheme.onSecondary
            )
        } else {
            if (finished) {
                onChange()
                finished = false
            }
            Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondary)
        }
    }
}
