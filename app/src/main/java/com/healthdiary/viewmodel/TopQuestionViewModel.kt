package com.healthdiary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.healthdiary.repository.TopQuestionRepository


class TopQuestionViewModel(val repository: TopQuestionRepository): ViewModel() {

    fun getTopQuestionEnglish() = repository.getTopQuestionEnglish()

    fun getTopQuestionChinese() = repository.getTopQuestionChinese()

    class Provider(private val repository: TopQuestionRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TopQuestionViewModel::class.java)) {
                return TopQuestionViewModel(repository) as T
            }

            throw IllegalArgumentException("Invalid viewmodel")
        }
    }
}