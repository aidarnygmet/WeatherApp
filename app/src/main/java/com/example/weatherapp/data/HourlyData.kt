package com.example.weatherapp.data

class hourlyData {
    var temp: String
    var time: String
    var descr: String
    constructor(temp: String, time:String, descr:String){
        this.temp = temp
        this.time = time
        this.descr = descr
    }
}