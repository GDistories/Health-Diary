package com.healthdiary.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healthdiary.R
import com.healthdiary.base.BaseActivity
import com.healthdiary.databinding.ActivityLoginBinding
import com.healthdiary.databinding.ActivityNewsWebContentBinding

class NewsWebContentActivity : BaseActivity() {
    private lateinit var binding: ActivityNewsWebContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsWebContentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.wvNewsContent.loadUrl("https://www.nbcnews.com/health/health-news/adults-say-expecting-stress-2023-survey-finds-rcna62580")
    }
}