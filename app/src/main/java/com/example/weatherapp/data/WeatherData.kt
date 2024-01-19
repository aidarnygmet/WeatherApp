package com.example.weatherapp.data

class WeatherData {
    var sunset: String
    var sunrise: String
    var humidity: String
    var temperature: String
    var feelsLike: String
    var wind: String
    var uvi: String
    var pressure: String
    var time: String
    var hourlyData: MutableList<hourlyData>
    var dailyData: MutableList<dailyData>
    constructor(
                sunset: String,
                sunrise: String,
                humidity: String,
                temperature: String,
                feelsLike: String,
                wind: String,
                uvi: String,
                pressure: String,
                time: String,
                hourlyData: MutableList<hourlyData>,
                dailyData: MutableList<dailyData>) {
        this.wind = wind
        this.sunset = sunset
        this.uvi = uvi
        this.sunrise = sunrise
        this.temperature = temperature
        this.pressure = pressure
        this.feelsLike = feelsLike
        this.humidity = humidity
        this.hourlyData = hourlyData
        this.time = time
        this.dailyData = dailyData
    }
}