package com.healthdiary.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    var name: String? = "",
    var email: String? = "",
    var phone: String? = "",
    var birthday: String? = "",
    var gender: String? = ""
) : Parcelable

