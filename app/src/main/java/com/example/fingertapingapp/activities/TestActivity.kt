package com.example.fingertapingapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import com.example.fingertapingapp.AlertDialogHelper
import com.example.fingertapingapp.R
import com.example.fingertapingapp.ToastHelper
import com.example.fingertapingapp.entities.Attempt
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

class TestActivity : AppCompatActivity() {

    private val instructionMessage = "To start test click any of two button. \n\n" +
            "Then, 30 seconds timer represented by bar on middle of screen will start counting down. " +
            "After timer reaches zero you result will be displayed, buttons will be disabled " +
            "and you will be redirected to results screen.\n\n" +
            "You can cancel the test anytime before it ends by pressing arrow in left top corner of screen."

    private val testDurationInMillis = 5000L
    var clickCounter = 0;
    var isTestStarted = false;
    var timer = 0;

    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        setSupportActionBar(findViewById(R.id.testToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        Log.d("DEBUG", intent.getSerializableExtra("USER").toString())

        initButtons()
        initProgressBar()

        AlertDialogHelper.showAlertDialog(this, instructionMessage, "Instruction")
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
        progressBar.max = 30000;
        progressBar.progress = 30000;
    }

    private fun handleBottomButtonClick() {
        if(!isTestStarted) {
            startTest();
        }

        findViewById<Button>(R.id.topButton).isEnabled = true
        findViewById<Button>(R.id.bottomButton).isEnabled = false

        clickCounter++;
    }

    private fun handleTopButtonClick() {
        if(!isTestStarted) {
            startTest();
        }

        findViewById<Button>(R.id.topButton).isEnabled = false
        findViewById<Button>(R.id.bottomButton).isEnabled = true

        clickCounter++;
    }

    private fun startTest() {
        isTestStarted = true
        ToastHelper.showShortToast(this, "Test started!")
        object : CountDownTimer(testDurationInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                progressBar.progress = millisUntilFinished.toInt()
            }

            override fun onFinish() {
                progressBar.progress = 0
                disableButtons()
                showResult()
                redirectAfterDelay()
            }

        }.start()
    }

    private fun showResult() {
        ToastHelper.showLongToast(this, "Result: $clickCounter")
    }

    private fun redirectAfterDelay() {
        val newAttempt = obtainNewAttempt()

        Timer("ResultDelay", false).schedule(object : TimerTask() {
            override fun run() {
                launchResultsActivity(newAttempt)
            }

        }, 2000)
    }

    private fun obtainNewAttempt(): Attempt {
        return Attempt(LocalDateTime.now(), clickCounter)
    }

    private fun launchResultsActivity(newAttempt: Attempt) {
        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("NEW_ATTEMPT", newAttempt)
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