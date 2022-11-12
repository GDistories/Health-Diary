package com.healthdiary.data

import com.healthdiary.bean.TopQuestions

open class TopQuestionData {
    companion object{
        fun getTopQuestionData(): ArrayList<TopQuestions.QuestionData> {
            val questionList = ArrayList<TopQuestions.QuestionData>()
            questionList.add(
                TopQuestions.QuestionData(1, "Programming Elective 1", "This is a tough subject")
            )
            questionList.add(
                TopQuestions.QuestionData(2, "Programming Elective 2", "This is a tough subject")
            )
            questionList.add(
                TopQuestions.QuestionData(3, "Programming Elective 3", "This is a tough subject")
            )
            questionList.add(
                TopQuestions.QuestionData(4, "Programming Elective 4", "This is a tough subject")
            )
            questionList.add(
                TopQuestions.QuestionData(5, "Programming Elective 5", "This is a tough subject")
            )
            questionList.add(
                TopQuestions.QuestionData(6, "Programming Elective 6", "This is a tough subject")
            )
            questionList.add(
                TopQuestions.QuestionData(7, "Programming Elective 7", "This is a tough subject")
            )
            questionList.add(
                TopQuestions.QuestionData(8, "Programming Elective 8", "This is a tough subject")
            )
            return questionList
        }
    }
}