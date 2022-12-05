package com.healthdiary.ui.activity

import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityEditInfoBinding

class EditInfoActivity : BaseActivity() {
    private lateinit var binding: ActivityEditInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnSubmitEdit.setOnClickListener{
            finish()
        }
    }
}