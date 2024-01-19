package com.example.weatherapp.service

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingService {
    @GET("reverse")
    fun coordinatesToName(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("limit") limit: String,
        @Query("appid") apiKey: String
    ): Call<JsonElement>
    @GET("direct")
    fun nameToCoordinates(
        @Query("q") q: String,
        @Query("limit") limit: String,
        @Query("appid") apiKey: String
    ): Call<JsonElement>
}