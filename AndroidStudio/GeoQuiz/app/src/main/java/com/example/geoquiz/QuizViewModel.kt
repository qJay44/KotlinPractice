package com.example.geoquiz

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel

class QuizViewModel : ViewModel() {

    companion object {
        private const val TAG = "QuizViewModel"
    }

    private var points = 0
    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true)
    )
    var currentIndex = 0
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    fun moveToPrev(): Int {
        if (currentIndex == 0) return -1
        currentIndex = (currentIndex - 1) % questionBank.size
        return 0
    }

    fun moveToNext(): Int {
        if (currentIndex == questionBank.size - 1) return -1
        currentIndex = (currentIndex + 1) % questionBank.size
        return 0
    }

    fun updateScore(ctx: Context, correctAnswer: Boolean, userAnswer: Boolean) {
        if (correctAnswer == userAnswer) points++
        if (currentIndex == questionBank.size - 1) {
            val successRate = (points / questionBank.size * 100.0f)
            val message = if (successRate > 100.0f) "100%" else successRate.toString().plus("%")
            Log.i(TAG, "Success rate: $message")

            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
        }
    }
}