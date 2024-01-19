package com.example.weatherapp.data

class dailyData {
    var day: String
    var sunrise: String
    var sunset: String
    var summary: String
    var tempDay: String
    var tempNight: String
    var feelsLikeDay: String
    var feelsLikeNight: String
    var pressure: String
    var humidity: String
    var wind: String
    var uvi: String
    constructor( day: String,
                 sunrise: String,
                 sunset: String,
                 summary: String,
                 tempDay: String,
                 tempNight: String,
                 feelsLikeDay: String,
                 feelsLikeNight: String,
                 pressure: String,
                 humidity: String,
                 wind: String,
                 uvi: String,){
        this.day =day
        this.sunrise=sunrise
        this.sunset=sunset
        this.summary=summary
        this.tempDay=tempDay
        this.tempNight=tempNight
        this.feelsLikeDay=feelsLikeDay
        this.feelsLikeNight=feelsLikeNight
        this.pressure=pressure
        this.humidity=humidity
        this.wind=wind
        this.uvi=uvi
    }
}