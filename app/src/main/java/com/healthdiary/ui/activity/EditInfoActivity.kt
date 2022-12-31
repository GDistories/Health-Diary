package com.healthdiary.ui.activity

import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityEditInfoBinding

class EditInfoActivity : BaseActivity() {

    var heightSelected: Int?=null
    var weightSelected: Int?=null

    private lateinit var binding: ActivityEditInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()


        binding.ivBack.setOnClickListener {
            heightSelected = binding.seekBar1.progress
            weightSelected = binding.seekBar2.progress
            finish()
        }
        binding.btnSubmitEdit.setOnClickListener{
            finish()
        }
    }
}