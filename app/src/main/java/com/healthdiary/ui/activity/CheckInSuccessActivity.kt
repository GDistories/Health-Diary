package com.healthdiary.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityAboutBinding
import com.healthdiary.databinding.ActivityCheckInBinding
import com.healthdiary.databinding.ActivityCheckInSuccessBinding

class CheckInSuccessActivity : BaseActivity() {
    private lateinit var binding: ActivityCheckInSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnCheckInClose.setOnClickListener {
            finish()
        }
    }
}