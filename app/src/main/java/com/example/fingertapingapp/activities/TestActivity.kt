package com.example.fingertapingapp.activities

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.fingertapingapp.R
import com.example.fingertapingapp.entities.Attempt
import com.example.fingertapingapp.entities.User
import com.example.fingertapingapp.helper.AlertDialogHelper
import com.example.fingertapingapp.helper.ToastHelper
import com.example.fingertapingapp.sqlliteutil.DatabaseFactory
import com.example.fingertapingapp.sqlliteutil.dao.AttemptDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*

class TestActivity : AppCompatActivity() {

    private val instructionMessage = "To start test click any of two button. \n\n" +
            "Then, 30 seconds timer represented by bar on middle of screen will start counting down. " +
            "After timer reaches zero you result will be displayed, buttons will be disabled " +
            "and you will be redirected to results screen.\n\n" +
            "You can cancel the test anytime before it ends by pressing arrow in left top corner of screen."

    private val testDurationInMillis = 30000L
    private var clickCounter = 0
    private var isTestStarted = false

    private lateinit var user: User
    private lateinit var attemptDao: AttemptDAO
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initToolbar()
        initDAO()
        initButtons()
        initProgressBar()

        setUser()
        showInstruction()
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.testToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initDAO() {
        attemptDao = DatabaseFactory.obtainDatabase(applicationContext)!!.attemptDao()
    }

    private fun initButtons() {
        findViewById<Button>(R.id.topButton).setOnClickListener {
            handleTopButtonClick()
        }

        findViewById<Button>(R.id.bottomButton).setOnClickListener {
            handleBottomButtonClick()
        }
    }

    private fun initProgressBar() {
        progressBar = findViewById(R.id.progressBar)
        progressBar.max = 30000
        progressBar.progress = 30000
    }

    private fun setUser() {
        user = intent.getSerializableExtra("USER") as User
        Log.d("DEBUG", user.toString())
    }

    private fun showInstruction() {
        AlertDialogHelper.showAlertDialog(this, instructionMessage, "Instruction")
    }

    private fun handleBottomButtonClick() {
        if (!isTestStarted) {
            startTest()
        }

        findViewById<Button>(R.id.topButton).isEnabled = true
        findViewById<Button>(R.id.bottomButton).isEnabled = false

        clickCounter++
    }

    private fun handleTopButtonClick() {
        if (!isTestStarted) {
            startTest()
        }

        findViewById<Button>(R.id.topButton).isEnabled = false
        findViewById<Button>(R.id.bottomButton).isEnabled = true

        clickCounter++
    }

    private fun startTest() {
        isTestStarted = true
        ToastHelper.showShortToast(this, "Test started!")

        startCountdown()
    }

    private fun startCountdown() {
        object : CountDownTimer(testDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                progressBar.progress = 0

                disableButtons()

                addAttemptToDataBase()

                showResult()
                redirectAfterDelay()
            }

        }.start()
    }

    private fun showResult() {
        ToastHelper.showLongToast(this, "Result: $clickCounter")
    }

    private fun addAttemptToDataBase() {
        val newAttempt = obtainNewAttempt()

        GlobalScope.launch(Dispatchers.IO) {
            attemptDao.insertAttempt(newAttempt)
        }
    }

    private fun redirectAfterDelay() {
        Timer("ResultDelay", false).schedule(object : TimerTask() {
            override fun run() {
                launchResultsActivity()
            }

        }, 2000)
    }

    private fun obtainNewAttempt(): Attempt {
        return Attempt(LocalDateTime.now(), clickCounter, user.userId)
    }

    private fun launchResultsActivity() {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("USER", user)
        }
        startActivity(intent)
    }

    private fun disableButtons() {
        findViewById<Button>(R.id.topButton).isEnabled = false
        findViewById<Button>(R.id.bottomButton).isEnabled = false
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
    }

    override fun onBackPressed() {}
}