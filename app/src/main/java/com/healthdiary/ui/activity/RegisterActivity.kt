package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityRegisterBinding

class RegisterActivity : BaseActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var passwordVisibility = false
    private var passwordConfirmVisibility = false
    private var sendHandler = Handler(Looper.getMainLooper())
    private var sendRunnable: Runnable? = null
    private var sendTime = 60


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

    }

    override fun onStart() {
        super.onStart()
        passwordVisibility = false
        passwordConfirmVisibility = false
        setPasswordVisibility()
        setPasswordConfirmVisibility()
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