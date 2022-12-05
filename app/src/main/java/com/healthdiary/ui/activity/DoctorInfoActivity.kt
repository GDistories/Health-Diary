package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import com.healthdiary.base.BaseActivity
import com.healthdiary.codepalace.chatbot.ui.ChatActivity
import com.healthdiary.databinding.ActivityDoctorInfoBinding


class DoctorInfoActivity : BaseActivity() {
    private lateinit var binding: ActivityDoctorInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoctorInfoBinding.inflate(layoutInflater)
        hideStatusAndActionBar()
        val view = binding.root
        setContentView(view)

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnStartChat.setOnClickListener {
            startActivity(Intent(this, ChatActivity::class.java))
        }
    }
}