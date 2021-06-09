package com.example.fingertapingapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fingertapingapp.R

class LogAsCaretakerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_as_caretaker)
        setSupportActionBar(findViewById(R.id.logAsCaretakerToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}