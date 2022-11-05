package com.healthdiary.ui.activity

import android.os.Bundle
import android.util.Log
import com.google.gson.Gson
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.bean.BMIRequest
import com.healthdiary.bean.BMIResult
import com.healthdiary.bean.CommonResponse
import com.healthdiary.databinding.ActivityBmiactivityBinding
import com.healthdiary.request.HealthCalculateServiceInterface
import okhttp3.FormBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BMIActivity : BaseActivity() {
    lateinit var binding : ActivityBmiactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        hideStatusAndActionBar()
        binding = ActivityBmiactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            val height = binding.editTextNumber.text.toString().toDouble()
            val weight = binding.editTextNumber2.text.toString().toDouble()
            val bmiRequest = BMIRequest(height,weight)
            getBMI(bmiRequest)
        }
    }

    fun getBMI(bmiRequest: BMIRequest){
        val retrofit = Retrofit.Builder()
            .baseUrl("http://apis.juhe.cn")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val request: HealthCalculateServiceInterface =
            retrofit.create(HealthCalculateServiceInterface::class.java)
        val body: RequestBody = FormBody.Builder()
            .add("key", bmiRequest.key!!)
            .add("height", bmiRequest.height.toString())
            .add("weight", bmiRequest.weight.toString())
            .add("role", bmiRequest.role.toString())
            .add("sex", bmiRequest.sex.toString())
            .build()

        val call: Call<CommonResponse?>? = request.getBMI(body)
        call!!.enqueue(object : retrofit2.Callback<CommonResponse?> {
            override fun onResponse(
                call: Call<CommonResponse?>,
                response: Response<CommonResponse?>
            ) {
                val responseBMI = response.body()
                if (responseBMI != null) {
                    if (responseBMI.error_code == 0) {
                        val bmiResult:BMIResult = Gson().fromJson(
                            responseBMI.result.toString(),
                            BMIResult::class.java
                        )
                        binding.textView.text = bmiResult.toString()

                    }
                }
            }

            override fun onFailure(call: Call<CommonResponse?>, t: Throwable) {
                Log.e("onFailure", "Failure" + t.message)
            }
        }
        )

    }
}