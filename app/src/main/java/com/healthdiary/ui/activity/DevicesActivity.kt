package com.healthdiary.ui.activity

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.RomUtils
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityDevicesBinding

class DevicesActivity : BaseActivity() {
    private lateinit var binding: ActivityDevicesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.ivAdd.setOnClickListener {
            val intent = packageManager.getLaunchIntentForPackage("com.huawei.health")
            if (intent != null) {
                startActivity(intent)
            }
        }
    }
}