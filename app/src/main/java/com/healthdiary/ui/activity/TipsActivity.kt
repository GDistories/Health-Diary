package com.healthdiary.ui.activity

import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityTipsBinding

class TipsActivity : BaseActivity() {
    private lateinit var binding: ActivityTipsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}