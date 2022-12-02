package com.healthdiary.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityAboutBinding
import com.healthdiary.databinding.ActivityCheckInBinding
import com.healthdiary.databinding.ActivityCheckInHistoryBinding

class CheckInActivity : BaseActivity() {
    private lateinit var binding: ActivityCheckInHistoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}