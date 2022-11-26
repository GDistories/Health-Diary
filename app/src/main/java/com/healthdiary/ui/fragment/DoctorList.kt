package com.healthdiary.ui.fragment

import androidx.annotation.DrawableRes

data class DoctorList (
    var doctorName: String,
    var doctorSymptons: String,

    @DrawableRes var doctorimg: Int)