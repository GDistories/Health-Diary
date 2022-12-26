package com.healthdiary.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    var name: String? = null,
    var email: String? = null,
    var phone: String? = null,
    var birthday: String? = null,
    var gender: String? = null
) : Parcelable

