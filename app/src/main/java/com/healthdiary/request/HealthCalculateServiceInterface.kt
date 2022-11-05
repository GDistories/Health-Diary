package com.healthdiary.request

import com.healthdiary.bean.CommonResponse
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface HealthCalculateServiceInterface {
    @POST("fapig/calculator/weight")
    fun getBMI(@Body requestBody: RequestBody): Call<CommonResponse?>?

    @POST("fapig/healthy/bmr")
    fun getBMR(@Body requestBody: RequestBody): Call<CommonResponse?>?
}

