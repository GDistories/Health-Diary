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

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModel.Provider(AuthRepository.repository)
    }


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
        val password = binding.etPassword.text.toString()
        val passwordConfirm = binding.etPasswordConfirm.text.toString()
        if (email.isEmpty()) {
            binding.etEmail.error = getString(R.string.email_is_required)
            binding.etEmail.requestFocus()
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
            binding.etPassword.error = getString(R.string.password_must_at_least_6_characters)
            binding.etPassword.requestFocus()
            return
        }
        if (password.length > 20) {
            binding.etPassword.error = getString(R.string.password_must_be_at_most_20_characters)
            binding.etPassword.requestFocus()
            return
        }
        if (!RegexUtils.isMatch("^(?=.*[a-zA-Z])(?=.*[0-9])[A-Za-z0-9]{6,20}\$", password)) {
            binding.etPassword.error = getString(R.string.password_must_contain_at_least_one_letter_and_one_number)
            binding.etPassword.requestFocus()
            return
        }
        if (!RegexUtils.isEmail(email)) {
            binding.etEmail.error = getString(R.string.not_a_valid_email)
            binding.etEmail.requestFocus()
            return
        }

        signUp(email, password)
    }

    private fun signUp(email: String, password: String) {
        viewModel.signUp(email, password).observe(this) {
            if (it) {
                ToastUtils.showShort(getString(R.string.sign_up_success))
                finish()
            } else {
                ToastUtils.showShort(getString(R.string.email_already_used))
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