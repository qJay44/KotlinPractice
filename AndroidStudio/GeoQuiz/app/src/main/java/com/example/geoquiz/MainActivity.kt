package com.example.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {

    private companion object {
        private const val TAG = "MainActivity"
        private const val KEY_INDEX = "index"
        private const val CHEAT_USED = "cheat"
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var prevButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button
    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }
    private val scan = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            quizViewModel.isCheater = intent?.getBooleanExtra("com.example.geoquiz.answer_shown", false) ?: false
            Log.i(TAG, quizViewModel.isCheater.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        prevButton.setOnClickListener {
            if (quizViewModel.moveToPrev() != -1) updateQuestion()
        }

        nextButton.setOnClickListener {
            if (quizViewModel.moveToNext() != -1) updateQuestion()
        }

        cheatButton.setOnClickListener {
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity, answerIsTrue)
            scan.launch(intent)
        }

        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        Log.i(TAG, "onSaveInstancesState")
        outPersistentState.putInt(KEY_INDEX, quizViewModel.currentIndex)
        outState.putBoolean(CHEAT_USED, quizViewModel.isCheater)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstancesState")

        val currentIndex = savedInstanceState.getInt(KEY_INDEX, 0)
        quizViewModel.currentIndex = currentIndex

        val isCheater = savedInstanceState.getBoolean(CHEAT_USED, false)
        quizViewModel.isCheater = isCheater
    }

    private fun updateQuestion() {
        val questionTextResIdNew = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResIdNew)

        trueButton.isEnabled = true
        falseButton.isEnabled = true
    }

    private fun checkAnswer(userAnswer: Boolean) {
        trueButton.isEnabled = false
        falseButton.isEnabled = false

        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == correctAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()

        quizViewModel.updateScore(this, correctAnswer, userAnswer)
    }
}
