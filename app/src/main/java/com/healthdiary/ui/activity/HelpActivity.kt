package com.healthdiary.ui.activity

import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.LogUtils
import com.healthdiary.adapter.TopQuestionAdapter
import com.healthdiary.base.BaseActivity
import com.healthdiary.data.TopQuestionData
import com.healthdiary.databinding.ActivityHelpBinding


class HelpActivity : BaseActivity() {
    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideStatusAndActionBar()

        buildRecyclerView()

    }


    private fun buildRecyclerView() {
        val topQuestionAdapter = TopQuestionAdapter()
        topQuestionAdapter.setResource(TopQuestionData.getTopQuestionData())
        val layout: RecyclerView.LayoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)

        binding.rvTopQuestions.layoutManager = layout
        binding.rvTopQuestions.itemAnimator = DefaultItemAnimator()
        binding.rvTopQuestions.itemAnimator?.changeDuration = 300
        binding.rvTopQuestions.itemAnimator?.moveDuration = 300
        binding.rvTopQuestions.adapter = topQuestionAdapter
    }


}