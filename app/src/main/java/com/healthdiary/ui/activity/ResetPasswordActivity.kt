package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityResetPasswordBinding
import com.healthdiary.repository.AuthRepository
import com.healthdiary.viewmodel.AuthViewModel

class ResetPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private var passwordVisibility = false
    private var passwordConfirmVisibility = false
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModel.Provider(AuthRepository.repository)
    }

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
            inputValidation()
        }

    }

    private fun inputValidation() {
        val email = binding.etEmail.text.toString()
        if (email.isEmpty()) {
            binding.etEmail.error = getString(R.string.email_is_required)
            binding.etEmail.requestFocus()
            return
        }
        if (!RegexUtils.isEmail(email)) {
            binding.etEmail.error = getString(R.string.please_enter_a_valid_email)
            binding.etEmail.requestFocus()
            return
        }

        resetPassword(email)
    }

    private fun resetPassword(email: String) {
        authViewModel.forgotPassword(email).observe(this) {
            if (it) {
                ToastUtils.showShort(getString(R.string.reset_password_email_sent))
                Handler(Looper.getMainLooper()).postDelayed({
                    finish()
                }, 2000)
            } else {
                ToastUtils.showShort(getString(R.string.email_not_found))
            }
        }
    }


    override fun onStart() {
        super.onStart()
        passwordVisibility = false
        passwordConfirmVisibility = false
    }




}