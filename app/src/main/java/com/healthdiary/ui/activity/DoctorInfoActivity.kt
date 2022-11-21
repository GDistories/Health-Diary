package com.healthdiary.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityDoctorInfoBinding


class DoctorInfoActivity : BaseActivity() {
    private lateinit var binding: ActivityDoctorInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorInfoBinding.inflate(layoutInflater)
        hideStatusAndActionBar()
        val view = binding.root
        setContentView(view)

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}