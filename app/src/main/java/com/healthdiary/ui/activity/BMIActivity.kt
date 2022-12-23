package com.healthdiary.ui.activity

import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.util.ClipboardUtils
import com.google.gson.Gson
import com.healthdiary.api.API
import com.healthdiary.api.RetrofitClient
import com.healthdiary.base.BaseActivity
import com.healthdiary.bean.BMIRequest
import com.healthdiary.bean.BMIResult
import com.healthdiary.bean.JuheResponse
import com.healthdiary.databinding.ActivityBmiactivityBinding
import okhttp3.FormBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response

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

    private fun getBMI(bmiRequest: BMIRequest){
        val retrofit = RetrofitClient.getInstance("http://apis.juhe.cn")

        val request: API = retrofit.create(API::class.java)

        val body: RequestBody = FormBody.Builder()
            .add("key", bmiRequest.key!!)
            .add("height", bmiRequest.height.toString())
            .add("weight", bmiRequest.weight.toString())
            .add("role", bmiRequest.role.toString())
            .add("sex", bmiRequest.sex.toString())
            .build()

        val call: Call<JuheResponse?>? = request.getBMI(body)

        call!!.enqueue(object : retrofit2.Callback<JuheResponse?> {
            override fun onResponse(
                call: Call<JuheResponse?>,
                response: Response<JuheResponse?>
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

            override fun onFailure(call: Call<JuheResponse?>, t: Throwable) {
                Log.e("onFailure", "Failure" + t.message)
            }
        }
        )

    }
}