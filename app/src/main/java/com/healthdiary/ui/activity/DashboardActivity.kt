package com.healthdiary.ui.activity

import android.R
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.github.mikephil.charting.charts.LineChart
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityDashboardBinding


class DashboardActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        // in this example, a LineChart is initialized from xml
        // LineChart lineChart1 = (LineChart) findViewById(R.id.chart1);

        binding.tvTitleDevices.text = "111";

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}