package com.example.weatherapp.viewmodel

import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.apiClient.GeocodingAPIClient
import com.example.weatherapp.apiClient.WeatherAPIClient
import com.example.weatherapp.data.WeatherData
import com.example.weatherapp.data.dailyData
import com.example.weatherapp.data.hourlyData
import com.example.weatherapp.service.GeocodingService
import com.example.weatherapp.service.OpenWeatherMapService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.math.roundToInt

suspend fun fetchDataFromWeatherApi(apiService: OpenWeatherMapService, lat:String, lon:String): Response<JsonElement> {
    return withContext(Dispatchers.IO) {
        val apiKey = "48dd284900f5f0d8e2116acda4cfbdb0"
        apiService.getCurrentWeatherData(lat,lon,"minutely,alerts", apiKey).execute()
    }
}
suspend fun coordinatesToNameCall(apiService: GeocodingService, lat:String, lon:String): Response<JsonElement> {
            return withContext(Dispatchers.IO) {
            val apiKey = "48dd284900f5f0d8e2116acda4cfbdb0"
            apiService.coordinatesToName(lat, lon,"5", apiKey).execute()
        }
    }
suspend fun nametoCoordinatesCall(apiService: GeocodingService, q:String): Response<JsonElement> {
    return withContext(Dispatchers.IO) {
        val apiKey = "48dd284900f5f0d8e2116acda4cfbdb0"
        apiService.nameToCoordinates(q,"5", apiKey).execute()
    }
}

class WeatherDataViewModel(fusedLocationProvideClient: FusedLocationProviderClient): ViewModel() {
    private var lat:String? = null
    private var lon:String? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient
    private val _myData : MutableStateFlow<MutableMap<String, WeatherData>?> = MutableStateFlow<MutableMap<String, WeatherData>?>(null)
    val myData : StateFlow<MutableMap<String, WeatherData>?>
        get() = _myData
    private val _searchData : MutableStateFlow<MutableMap<String, WeatherData>?> = MutableStateFlow<MutableMap<String, WeatherData>?>(null)
    val searchData : StateFlow<MutableMap<String, WeatherData>?>
        get() = _searchData
    private val _locationState = MutableStateFlow<Location?>(null)
    val locationState: StateFlow<Location?> = _locationState

    init {
        this.fusedLocationProviderClient = fusedLocationProvideClient
        Log.d("check", "init viewModel")
        fetchLocation()
    }

    private fun fetchLocation() {
        try {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location ->
                    Log.d("check", "Location: "+location.toString())
                    _locationState.value = location
                }
                .addOnFailureListener { e ->
                    Log.d("check", "Failed to get location"+e.message.toString())
                }
        } catch (e: SecurityException) {
            Log.d("check", "Security Exception"+e.message.toString())
        }
    }
    suspend fun addData(location: String): Boolean{
        deleteSearchData()
        var addDataStatus = false
        suspendCancellableCoroutine<Unit> { continuation ->
        viewModelScope.launch{
            try {
                val geocodingService: GeocodingService = GeocodingAPIClient.getInstance().create(
                    GeocodingService::class.java
                )
                nametoCoordinatesCall(geocodingService, location).body()?.asJsonArray?.forEach {
                    fetchSearchData(it.asJsonObject["lat"].toString(), it.asJsonObject["lon"].toString())
                }
                addDataStatus = true
                continuation.resume(Unit)
            } catch (e: Exception) {
                Log.d("check", e.message.toString())
                continuation.resume(Unit)
            }
        }
        }
        return addDataStatus
    }
    fun deleteSearchData(){
        _searchData.value = mutableMapOf()
    }
    suspend fun updateMap(newEntry: Map.Entry<String, WeatherData>): Boolean{
        var resp = false
        suspendCancellableCoroutine<Unit> { continuation ->
            viewModelScope.launch {
                try{
                    _myData.value?.set(newEntry.key, newEntry.value)
                    resp = true
                }
                catch (e: Exception){
                    Log.d("check", e.message.toString())
                }
                continuation.resume(Unit)
            }
        }
        return resp
    }
    fun setLocation(lat: String, lon: String){
        this.lat = lat
        this.lon = lon
    }
    suspend fun fetchData(): Boolean {
        val lat = this.lat
        val lon = this.lon
        if(lat != null && lon != null){
            suspendCancellableCoroutine<Unit> { continuation ->
            viewModelScope.launch {
                try {
                    val openWeatherMapService: OpenWeatherMapService =
                        WeatherAPIClient.getInstance().create(
                            OpenWeatherMapService::class.java
                        )
                    val response = fetchDataFromWeatherApi(openWeatherMapService, lat, lon)

                    val geocodingService: GeocodingService =
                        GeocodingAPIClient.getInstance().create(GeocodingService::class.java)
                    val georesponse = coordinatesToNameCall(geocodingService, lat, lon)
                    val locationName =
                        (georesponse.body()?.asJsonArray?.get(0)?.asJsonObject?.getAsJsonPrimitive("name")
                            .toString().removeSurrounding("\"")+"!"+georesponse.body()?.asJsonArray?.get(0)?.asJsonObject?.getAsJsonPrimitive("state").toString().removeSurrounding("\"")
                    +"!"+georesponse.body()?.asJsonArray?.get(0)?.asJsonObject?.getAsJsonPrimitive("country").toString().removeSurrounding("\""))
                    if(_myData.value == null){
                        _myData.value = mutableMapOf()
                    }
                    val weatherData = getWeatherDataFromJson(response.body()?.asJsonObject)!!
                    _myData.value!![locationName] = weatherData
                    Log.d(
                        "check",
                        "myData: " + _myData.value?.get(locationName)?.descr.toString()
                    )
                    continuation.resume(Unit)
                } catch (e: Exception) {
                    Log.d("check", e.message.toString())
                    continuation.resume(Unit)
                }
            }
            }
        } else {
            Log.d("check", "can't get location data")
        }
        Log.d("check", "map size: "+_myData.value?.size.toString())
        return _myData.value?.size != 0
    }
    private suspend fun fetchSearchData(lat: String, lon: String):Boolean{
        var res = false
        suspendCancellableCoroutine<Unit> { continuation ->
            viewModelScope.launch {
                try {
                    val openWeatherMapService: OpenWeatherMapService =
                        WeatherAPIClient.getInstance().create(
                            OpenWeatherMapService::class.java
                        )
                    val response = fetchDataFromWeatherApi(openWeatherMapService, lat, lon)

                    val geocodingService: GeocodingService =
                        GeocodingAPIClient.getInstance().create(GeocodingService::class.java)
                    val georesponse = coordinatesToNameCall(geocodingService, lat, lon)
                    val locationName =
                        (georesponse.body()?.asJsonArray?.get(0)?.asJsonObject?.getAsJsonPrimitive("name")
                            .toString().removeSurrounding("\"")+"!"+georesponse.body()?.asJsonArray?.get(0)?.asJsonObject?.getAsJsonPrimitive("state").toString().removeSurrounding("\"")
                                +"!"+georesponse.body()?.asJsonArray?.get(0)?.asJsonObject?.getAsJsonPrimitive("country").toString().removeSurrounding("\""))
                    if(_searchData.value == null){
                        _searchData.value = mutableMapOf()
                    }
                    val weatherData = getWeatherDataFromJson(response.body()?.asJsonObject)!!
                    _searchData.value!![locationName] = weatherData
                    res = true
                    continuation.resume(Unit)
                } catch (e: Exception) {
                    Log.d("check", e.message.toString())
                    continuation.resume(Unit)
                }
            }
        }
        return res
    }


    private fun getWeatherDataFromJson(response: JsonObject?): WeatherData? {
        if(response is JsonObject){
            val body = response.getAsJsonObject("current")
            val timezone = response.getAsJsonPrimitive("timezone").toString().removeSurrounding("\"")
            val weather= WeatherData("null","null","null","null","null","null","null","null", "null",
                MutableList(0){ hourlyData("0", "0", "null","null", "null", "null") },
                MutableList(0){ dailyData("null","null","null","null","null","null","null","null","null","null","null","null", "null", "null") }
            ,"null","null")
            val instantSunrise = Instant.ofEpochSecond(body["sunrise"].toString().toLong())
            val instantSunset = Instant.ofEpochSecond(body["sunset"].toString().toLong())
            val instantTime = Instant.ofEpochSecond(body["dt"].toString().toLong())
            val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of(timezone))

            weather.temperature = (body["temp"].toString().toFloat()-273.15).roundToInt().toString()
            weather.sunrise = formatter.format(instantSunrise)
            weather.sunset = formatter.format(instantSunset)
            weather.feelsLike = (body["feels_like"].toString().toFloat()-273.15).roundToInt().toString()
            weather.pressure = body["pressure"].toString()
            weather.humidity = body["humidity"].toString()
            weather.uvi = body["uvi"].toString()
            weather.wind = body["wind_speed"].toString()
            weather.wind_deg = body["wind_deg"].toString()
            weather.descr = body.getAsJsonArray("weather").get(0).asJsonObject["id"].toString().removeSurrounding("\"")
            weather.time = formatter.format(instantTime)
            weather.hourlyData = getHourlyData(response.getAsJsonArray("hourly"), timezone)
            weather.dailyData = getDailyData(response.getAsJsonArray("daily"), timezone)
            return weather
        } else {
            return null
        }

    }

    private fun getDailyData(obj: JsonArray?, timezone: String): MutableList<dailyData> {
        val dailyData = MutableList(0){ dailyData("null","null","null","null","null","null","null","null","null","null","null","null", "null", "null") }
        if (obj != null) {
            for (o in obj){
                val instantDay = Instant.ofEpochSecond(o.asJsonObject["dt"].toString().toLong())
                val instantSunrise = Instant.ofEpochSecond(o.asJsonObject["sunrise"].toString().toLong())
                val instantSunset = Instant.ofEpochSecond(o.asJsonObject["sunset"].toString().toLong())
                val formatterDay = DateTimeFormatter.ofPattern("dd/MM").withZone(ZoneId.of(timezone))
                val formatterHour = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of(timezone))

                val dailyDataObject = dailyData(
                    formatterDay.format(instantDay),
                    formatterHour.format(instantSunrise),
                    formatterHour.format(instantSunset),
                    o.asJsonObject["summary"].toString().removeSurrounding("\""),
                    (o.asJsonObject.getAsJsonObject("temp").getAsJsonPrimitive("day").asDouble-273.15).roundToInt().toString(),
                    (o.asJsonObject.getAsJsonObject("temp").getAsJsonPrimitive("night").asDouble-273.15).roundToInt().toString(),
                    (o.asJsonObject.getAsJsonObject("feels_like").getAsJsonPrimitive("day").asDouble-273.15).roundToInt().toString(),
                    (o.asJsonObject.getAsJsonObject("feels_like").getAsJsonPrimitive("night").asDouble-273.15).roundToInt().toString(),
                    o.asJsonObject["pressure"].toString(),
                    o.asJsonObject["humidity"].toString(),
                    o.asJsonObject["wind_speed"].toString(),
                    o.asJsonObject["uvi"].toString(),
                    o.asJsonObject.getAsJsonArray("weather").get(0).asJsonObject["id"].toString(),
                    o.asJsonObject["wind_deg"].toString()
                )
                dailyData.add(dailyDataObject)
            }
        }
        return dailyData
    }

    private fun getHourlyData(obj: JsonArray?, timezone: String): MutableList<hourlyData> {
        val hourlyData= MutableList(0){ hourlyData("0", "0", "null", "null", "null", "null") }
        if (obj != null) {
            for (o in obj){
                val instant = Instant.ofEpochSecond(o.asJsonObject["dt"].toString().toLong())
                val formatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of(timezone))
                val hourlyDataObject = hourlyData((o.asJsonObject["temp"].toString().toFloat()-273.15).roundToInt().toString(),
                    formatter.format(instant),
                    o.asJsonObject.getAsJsonArray("weather").get(0).asJsonObject["id"].toString(),
                    o.asJsonObject["wind_speed"].toString(),
                    o.asJsonObject["wind_deg"].toString(),
                    o.asJsonObject["humidity"].toString())
                hourlyData.add(hourlyDataObject)
                }

        }

        return hourlyData
    }


}
