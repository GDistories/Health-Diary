package com.healthdiary.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.databinding.ActivityAboutBinding
import com.healthdiary.databinding.ActivityEditInfoBinding

class EditInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }
}