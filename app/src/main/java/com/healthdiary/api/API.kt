package com.healthdiary.api

import com.healthdiary.bean.JuheResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("fapig/calculator/weight")
    fun getBMI(@Body requestBody: RequestBody): Call<JuheResponse?>?

    @POST("fapig/healthy/bmr")
    fun getBMR(@Body requestBody: RequestBody): Call<JuheResponse?>?
}