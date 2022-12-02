package com.healthdiary.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.databinding.ActivityAboutBinding
import com.healthdiary.databinding.ActivityTipsBinding

class TipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTipsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}