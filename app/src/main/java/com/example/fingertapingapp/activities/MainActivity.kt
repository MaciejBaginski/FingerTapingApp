package com.example.fingertapingapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.fingertapingapp.AlertDialogHelper
import com.example.fingertapingapp.R
import com.example.fingertapingapp.ValidationException
import com.example.fingertapingapp.Validator
import com.example.fingertapingapp.entities.User
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.mainToolbar))

        findViewById<FloatingActionButton>(R.id.logFAB).setOnClickListener {
            handleLoginAction()
        }
    }

    private fun handleLoginAction() {
        try {
            validateFields()
            val user = createNewUserFromDataFields()
            launchTestActivity(user)
        } catch (e: ValidationException) {
            AlertDialogHelper.showAlertDialog(this, e)
        }
    }

    private fun validateFields() {
        val name = findViewById<EditText>(R.id.nameTextBox).text.toString()
        val surname = findViewById<EditText>(R.id.surnameTextBox).text.toString()
        val ageAsString = findViewById<EditText>(R.id.ageTextBox).text.toString()

        Validator.validateUserLoginData(name, surname, ageAsString)
    }



    private fun createNewUserFromDataFields(): User {
        val name = findViewById<EditText>(R.id.nameTextBox).text.toString()
        val surname = findViewById<EditText>(R.id.surnameTextBox).text.toString()
        val age = findViewById<EditText>(R.id.ageTextBox).text.toString().toInt()

        return User(name, surname, age)
    }

    private fun launchTestActivity(user: User) {

        val intent = Intent(this, TestActivity::class.java).apply {
            putExtra("USER", user)
        }
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logAsCaretaker -> {
                launchLogAsCaretakerActivity();
                true;
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun launchLogAsCaretakerActivity() {
        val intent = Intent(this, LogAsCaretakerActivity::class.java)
        startActivity(intent)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.clear()
        menuInflater.inflate(R.menu.appbar_login_menu, menu)
        return super.onPrepareOptionsMenu(menu)
    }
}