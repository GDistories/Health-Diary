package com.healthdiary.ui.activity

import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.util.Log
import com.blankj.utilcode.util.RegexUtils
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var passwordVisibility = false
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        hideStatusAndActionBar()
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

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
        binding.btnSignIn.setOnClickListener {
            inputValidation()
        }
    }

    override fun onStart() {
        super.onStart()
        passwordVisibility = false
        setPasswordVisibility()
    }

    private fun inputValidation() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
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
        if (password.isEmpty()) {
            binding.etPassword.error = getString(R.string.password_is_required)
            binding.etPassword.requestFocus()
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

        signIn()
    }

    private fun signIn() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        //TODO: Implement sign in
        lateinit var auth: FirebaseAuth

        finish()
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
}