package com.healthdiary.ui.activity

import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivitySleepReportBinding


class SleepReportActivity : BaseActivity() {
    private lateinit var binding: ActivitySleepReportBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySleepReportBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}