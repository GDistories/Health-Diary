package com.healthdiary.ui.activity

import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityScoreBinding

class ScoreActivity : BaseActivity() {
    private lateinit var binding: ActivityScoreBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}