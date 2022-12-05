package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityTrackerBinding

class TrackerActivity : BaseActivity() {
    private lateinit var binding: ActivityTrackerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnGoDashboard.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
        binding.btnGoSleepReport.setOnClickListener {
            startActivity(Intent(this, SleepReportActivity::class.java))
        }
    }
}