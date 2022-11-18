package com.healthdiary.bean

class BMIRequest(height: Number?, weight: Number?) {
    var key : String? = "1c9155cf4d4242b86f2ec96e87c809d2"
    var sex : Int = 1
    var role : Int = 1
    var height : Number? = null
    var weight : Number? = null

    init {
        this.height = height
        this.weight = weight
    }
}