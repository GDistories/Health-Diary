package com.healthdiary.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.databinding.ActivityAboutBinding
import com.healthdiary.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}