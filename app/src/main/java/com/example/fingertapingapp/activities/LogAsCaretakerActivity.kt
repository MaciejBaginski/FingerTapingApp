package com.example.fingertapingapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.fingertapingapp.R
import com.example.fingertapingapp.entities.Caretaker
import com.example.fingertapingapp.helper.AlertDialogHelper
import com.example.fingertapingapp.sqlliteutil.DatabaseFactory
import com.example.fingertapingapp.sqlliteutil.dao.CaretakerDAO
import com.example.fingertapingapp.validator.ValidationException
import com.example.fingertapingapp.validator.Validator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.SQLException

class LogAsCaretakerActivity : AppCompatActivity() {

    private lateinit var caretakerDao: CaretakerDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_as_caretaker)
        initToolbar()

        initDAO()
        initFAB()
    }

    private fun initDAO() {
        caretakerDao = DatabaseFactory.obtainDatabase(applicationContext)!!.caretakerDao()
    }

    private fun initFAB() {
        findViewById<FloatingActionButton>(R.id.logAsCaretakerFAB).setOnClickListener {
            handleLoginAction()
        }
    }

    private fun handleLoginAction() {
        try {

            val login = findViewById<EditText>(R.id.loginTextBox).text.toString()
            val password = findViewById<EditText>(R.id.passwordTextBox).text.toString()

            validateFields(login, password)

            GlobalScope.launch(Dispatchers.IO) {
                logIn(this, login, password)
            }

        } catch (e: ValidationException) {
            handleValidationException(e)
        }
    }

    private fun logIn(coroutineScope: CoroutineScope, login: String, password: String) {
        try {

            val caretaker = loginAsCaretaker(login, password)
            coroutineScope.launch(Dispatchers.Main) {
                launchResultActivity(caretaker)
            }

        } catch (e: ValidationException) {
            coroutineScope.launch(Dispatchers.Main) {
                handleValidationException(e)
            }

        } catch (e: SQLException) {
            coroutineScope.launch(Dispatchers.Main) {
                handleSQLException(e)
            }

        }
    }

    private fun handleSQLException(e: SQLException) {
        AlertDialogHelper.showAlertDialog(
            this@LogAsCaretakerActivity,
            "Error during accessing database",
            "Database Error"
        )
        Log.e("DB_ACCESS", e.message.toString())
    }

    private fun handleValidationException(e: ValidationException) {
        AlertDialogHelper.showAlertDialog(this@LogAsCaretakerActivity, e)
    }

    private fun loginAsCaretaker(login: String, password: String): Caretaker {
        return caretakerDao.getCaretaker(login, password)
            ?: throw ValidationException("Incorrect login or password.")
    }

    private fun validateFields(login: String, password: String) {
        Validator.validateCaretakerLoginData(login, password)
    }

    private fun launchResultActivity(caretaker: Caretaker) {

        val intent = Intent(this, ResultActivity::class.java).apply {
            putExtra("CARETAKER", caretaker)
        }
        startActivity(intent)
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.logAsCaretakerToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}