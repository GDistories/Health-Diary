package com.healthdiary.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class TopQuestions {
    private val success = false
    private val message = ""
    private val data: List<QuestionData>? = null

    @Parcelize
    class QuestionData(
        private val questionId: String,
        val title: String,
        val description: String) : Parcelable

}