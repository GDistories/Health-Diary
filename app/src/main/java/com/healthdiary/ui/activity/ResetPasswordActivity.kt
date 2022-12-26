package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityResetPasswordBinding

class ResetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private var passwordVisibility = false
    private var passwordConfirmVisibility = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        hideStatusAndActionBar()
        val view = binding.root
        setContentView(view)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivEmailRemove.setOnClickListener {
            binding.etEmail.setText("")
        }

        binding.btnResetPassword.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
    override fun onStart() {
        super.onStart()
        passwordVisibility = false
        passwordConfirmVisibility = false
    }




}