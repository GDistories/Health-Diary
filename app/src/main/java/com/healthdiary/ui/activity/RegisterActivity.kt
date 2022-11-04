package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var passwordVisibility = false;
    private var passwordConfirmVisibility = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        hideStatusAndActionBar()
        val view = binding.root
        setContentView(view)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivEmailRemove.setOnClickListener {
            binding.etEmail.setText("")
        }
        binding.ivVerifyCodeRemove.setOnClickListener {
            binding.etVerifyCode.setText("")
        }
        binding.ivPasswordVisibility.setOnClickListener {
            passwordVisibility = !passwordVisibility
            setPasswordVisibility()
        }
        binding.ivPasswordConfirmVisibility.setOnClickListener {
            passwordConfirmVisibility = !passwordConfirmVisibility
            setPasswordConfirmVisibility()
        }
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        passwordVisibility = false
        passwordConfirmVisibility = false
        setPasswordVisibility()
        setPasswordConfirmVisibility()
    }

    private fun setPasswordVisibility(){
        if(passwordVisibility) {
            binding.ivPasswordVisibility.setImageResource(R.drawable.ic_visible)
            binding.etPassword.inputType = 0x91
            binding.etPassword.setSelection(binding.etPassword.text.length)
        } else {
            binding.ivPasswordVisibility.setImageResource(R.drawable.ic_invisible)
            binding.etPassword.inputType = 0x81
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }
    }

    private fun setPasswordConfirmVisibility(){
        if(passwordConfirmVisibility) {
            binding.ivPasswordConfirmVisibility.setImageResource(R.drawable.ic_visible)
            binding.etPasswordConfirm.inputType = 0x91
            binding.etPasswordConfirm.setSelection(binding.etPasswordConfirm.text.length)
        } else {
            binding.ivPasswordConfirmVisibility.setImageResource(R.drawable.ic_invisible)
            binding.etPasswordConfirm.inputType = 0x81
            binding.etPasswordConfirm.setSelection(binding.etPasswordConfirm.text.length)
        }
    }
}