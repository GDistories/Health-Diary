package com.healthdiary.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.databinding.ActivityAboutBinding
import com.healthdiary.databinding.ActivityCheckInBinding

class CheckInHistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckInBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}