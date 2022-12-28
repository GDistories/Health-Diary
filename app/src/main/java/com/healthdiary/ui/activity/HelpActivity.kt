package com.healthdiary.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LanguageUtils
import com.blankj.utilcode.util.LogUtils
import com.healthdiary.adapter.TopQuestionAdapter
import com.healthdiary.base.BaseActivity
import com.healthdiary.bean.TopQuestions
import com.healthdiary.databinding.ActivityHelpBinding
import com.healthdiary.repository.TopQuestionRepository
import com.healthdiary.viewmodel.TopQuestionViewModel


class HelpActivity : BaseActivity() {
    private lateinit var binding: ActivityHelpBinding

    private val topQuestionViewModel: TopQuestionViewModel by viewModels {
        TopQuestionViewModel.Provider(TopQuestionRepository.repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        val language = LanguageUtils.getContextLanguage(this)
        if (language.toString() == "zh_CN" || language.toString() == "zh_TW") {
            topQuestionViewModel.getTopQuestionChinese().observe(this){
                buildRecyclerView(it)
            }
        } else {
            topQuestionViewModel.getTopQuestionEnglish().observe(this){
                buildRecyclerView(it)
            }
        }
    }


    private fun buildRecyclerView(topQuestionData: List<TopQuestions.QuestionData>) {
        val topQuestionAdapter = TopQuestionAdapter()
        topQuestionAdapter.setResource(topQuestionData)
        val layout: RecyclerView.LayoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)

        binding.rvTopQuestions.layoutManager = layout
        binding.rvTopQuestions.itemAnimator = DefaultItemAnimator()
        binding.rvTopQuestions.itemAnimator?.changeDuration = 300
        binding.rvTopQuestions.itemAnimator?.moveDuration = 300
        binding.rvTopQuestions.adapter = topQuestionAdapter
    }


}