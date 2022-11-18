package com.healthdiary.bean

class BMIResult {
    var idealWeight: Number? = null
    var normalWeight: String? = null
    var level: Int? = null
    var levelMsg: String? = null
    var danger: String? = null
    var bmi : Number? = null
    var normalBMI : String? = null

override fun toString(): String {
        return "BMIResult(idealWeight=$idealWeight, normalWeight=$normalWeight, level=$level, levelMsg=$levelMsg, danger=$danger, bmi=$bmi, normalBMI=$normalBMI)"
    }

}