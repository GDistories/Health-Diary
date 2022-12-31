package com.healthdiary.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.healthdiary.base.BaseActivity
import com.healthdiary.codepalace.chatbot.ui.ChatActivity
import com.healthdiary.databinding.ActivityCheckInSuccessBinding

class CheckInSuccessActivity : BaseActivity() {
    private lateinit var binding: ActivityCheckInSuccessBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInSuccessBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.checkInShare.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"I have done check-in today")
            intent.type="text/plain"

            startActivity(Intent.createChooser(intent, "Please select app on the phone"))
        }

        binding.shareWithProfessionals.setOnClickListener {
            val intent = Intent(this,ChatActivity::class.java);
            intent.putExtra(Intent.EXTRA_TEXT,"I have done check-in today")
            startActivity(intent);

        }

        binding.ivBack.setOnClickListener {
            finish()
        }
        binding.btnCheckInClose.setOnClickListener {
            finish()
        }
    }
}