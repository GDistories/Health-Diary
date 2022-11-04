package com.healthdiary.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    var passwordVisibility = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        hideStatusAndActionBar()
        val view = binding.root
        setContentView(view)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.ivEmailRemove.setOnClickListener {
            binding.etEmail.setText("")
        }
        binding.ivPasswordVisibility.setOnClickListener {
            passwordVisibility = !passwordVisibility
            setPasswordVisibility()
        }
        binding.tvForgotPassword.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        passwordVisibility = false
        setPasswordVisibility()
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
}