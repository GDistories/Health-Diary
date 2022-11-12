package com.healthdiary.bean

class TopQuestions {
    private val success = false
    private val message = ""
    private val data: List<QuestionData>? = null

    public class QuestionData(private val questionId: Int, val title: String, val description: String) {

        override fun toString(): String {
            return "Data(questionId=$questionId, title='$title', description='$description')"
        }
    }

    override fun toString(): String {
        return "TopQuestions(success=$success, message='$message')"
    }

}