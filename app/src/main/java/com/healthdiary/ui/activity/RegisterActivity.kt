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
import com.healthdiary.databinding.ActivityRegisterBinding
import com.healthdiary.repository.AuthRepository
import com.healthdiary.viewmodel.AuthViewModel

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var passwordVisibility = false
    private var passwordConfirmVisibility = false
    private var sendHandler = Handler(Looper.getMainLooper())
    private var sendRunnable: Runnable? = null
    private var sendTime = 60

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModel.Provider(AuthRepository.repository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        hideStatusAndActionBar()
        val view = binding.root
        setContentView(view)

        sendRunnable = Runnable {
            var time = binding.btnSendEmail.text.toString().replace("s", "").toInt()
            if (time > 0) {
                time--
                binding.btnSendEmail.text = "$time"+"s"
                sendHandler.postDelayed(sendRunnable!!, 1000)
            } else {
                binding.btnSendEmail.isEnabled = true
                binding.btnSendEmail.text = "Send"
                binding.btnSendEmail.background = getDrawable(R.drawable.btn_blue)
                binding.btnSendEmail.setTextColor(getColor(R.color.white))
            }
        }


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
        binding.btnSendEmail.setOnClickListener {
            binding.btnSendEmail.isEnabled = false
            binding.btnSendEmail.text = sendTime.toString() + "s"
            binding.etVerifyCode.requestFocus()
            binding.btnSendEmail.background = getDrawable(R.drawable.bg_search)
            binding.btnSendEmail.setTextColor(getColor(R.color.silver_gray))
            sendHandler.postDelayed(sendRunnable!!, 1000)
        }

        binding.btnSignUp.setOnClickListener {
            inputValidation()

        }

    }

    override fun onStart() {
        super.onStart()
        passwordVisibility = false
        passwordConfirmVisibility = false
        setPasswordVisibility()
        setPasswordConfirmVisibility()
    }

    private fun inputValidation(){
        val email = binding.etEmail.text.toString()
        val verifyCode = binding.etVerifyCode.text.toString()
        val password = binding.etPassword.text.toString()
        val passwordConfirm = binding.etPasswordConfirm.text.toString()
        if (email.isEmpty()) {
            binding.etEmail.error = getString(R.string.email_is_required)
            binding.etEmail.requestFocus()
            return
        }
        if (verifyCode.isEmpty()) {
            binding.etVerifyCode.error = getString(R.string.verify_code_is_required)
            binding.etVerifyCode.requestFocus()
            return
        }
        if (password.isEmpty()) {
            binding.etPassword.error = getString(R.string.password_is_required)
            binding.etPassword.requestFocus()
            return
        }
        if (passwordConfirm.isEmpty()) {
            binding.etPasswordConfirm.error = getString(R.string.password_confirm_is_required)
            binding.etPasswordConfirm.requestFocus()
            return
        }
        if (password != passwordConfirm) {
            binding.etPasswordConfirm.error = getString(R.string.password_confirm_is_not_match)
            binding.etPasswordConfirm.requestFocus()
            return
        }
        if (password.length < 6) {
            binding.etPassword.error = getString(R.string.password_length_is_too_short)
            binding.etPassword.requestFocus()
            return
        }
        if (RegexUtils.isEmail(email)) {
            binding.etEmail.error = getString(R.string.not_a_valid_email)
            binding.etEmail.requestFocus()
            return
        }

        signUp(email, password)
    }

    private fun signUp(email: String, password: String) {
        viewModel.signUp(email, password).observe(this) {
            if (it) {
                ToastUtils.showShort("Sign up successfully")
            } else {
                ToastUtils.showShort("Sign up failed")
            }
        }
    }

    private fun setPasswordVisibility(){
        val typeface = resources.getFont(R.font.dmsans_medium)
        if(passwordVisibility) {
            binding.ivPasswordVisibility.setImageResource(R.drawable.ic_visible)
            binding.etPassword.inputType = 0x91
            binding.etPassword.typeface = typeface
            binding.etPassword.setSelection(binding.etPassword.text.length)
        } else {
            binding.ivPasswordVisibility.setImageResource(R.drawable.ic_invisible)
            binding.etPassword.inputType = 0x81
            binding.etPassword.typeface = typeface
            binding.etPassword.setSelection(binding.etPassword.text.length)
        }
    }

    private fun setPasswordConfirmVisibility(){
        val typeface = resources.getFont(R.font.dmsans_medium)
        if(passwordConfirmVisibility) {
            binding.ivPasswordConfirmVisibility.setImageResource(R.drawable.ic_visible)
            binding.etPasswordConfirm.inputType = 0x91
            binding.etPasswordConfirm.typeface = typeface
            binding.etPasswordConfirm.setSelection(binding.etPasswordConfirm.text.length)
        } else {
            binding.ivPasswordConfirmVisibility.setImageResource(R.drawable.ic_invisible)
            binding.etPasswordConfirm.inputType = 0x81
            binding.etPasswordConfirm.typeface = typeface
            binding.etPasswordConfirm.setSelection(binding.etPasswordConfirm.text.length)
        }
    }
}