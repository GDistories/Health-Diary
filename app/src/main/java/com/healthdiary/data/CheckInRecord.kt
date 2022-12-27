package com.healthdiary.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class CheckInRecord(
    var temperature:String? = "",
    var heartRate: String? = "",
    var symptom: String? = "",
    var medicine: String? = "",
) : Parcelable