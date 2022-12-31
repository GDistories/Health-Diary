package com.healthdiary.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.healthdiary.bean.TopQuestions

class TopQuestionRepository {
    private val db = Firebase.firestore

    fun getTopQuestionEnglish() : MutableLiveData<List<TopQuestions.QuestionData>> {
        val topQuestionLiveData: MutableLiveData<List<TopQuestions.QuestionData>> = MutableLiveData()

        val questions = ArrayList<TopQuestions.QuestionData>()

        val TAG = "getTopQuestion"

        db.collection("TopQuestion_en_US")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val question =
                        TopQuestions.QuestionData(
                            document.id,
                            document.data["title"].toString(),
                            document.data["description"].toString()
                        )
                    questions.add(question)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                topQuestionLiveData.value = questions
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        return topQuestionLiveData
    }

    fun getTopQuestionChinese() : MutableLiveData<List<TopQuestions.QuestionData>> {
        val topQuestionLiveData: MutableLiveData<List<TopQuestions.QuestionData>> = MutableLiveData()

        val questions = ArrayList<TopQuestions.QuestionData>()

        val TAG = "getTopQuestion"

        db.collection("TopQuestion_zh_CN")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val question =
                        TopQuestions.QuestionData(
                            document.id,
                            document.data["title"].toString(),
                            document.data["description"].toString()
                        )
                    questions.add(question)
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
                topQuestionLiveData.value = questions
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }

        return topQuestionLiveData
    }

    companion object {
        val repository = TopQuestionRepository()
    }
}