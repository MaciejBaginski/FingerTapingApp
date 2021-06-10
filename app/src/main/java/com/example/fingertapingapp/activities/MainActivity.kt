package com.example.fingertapingapp.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.fingertapingapp.R
import com.example.fingertapingapp.entities.User
import com.example.fingertapingapp.helper.AlertDialogHelper
import com.example.fingertapingapp.sqlliteutil.DatabaseFactory
import com.example.fingertapingapp.sqlliteutil.dao.UserDAO
import com.example.fingertapingapp.validator.ValidationException
import com.example.fingertapingapp.validator.Validator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.SQLException


class MainActivity : AppCompatActivity() {

    private lateinit var userDao: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initToolbar()

        initDAO()
        initFAB()
    }

    private fun initToolbar() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.mainToolbar))
    }

    private fun initDAO() {
        userDao = DatabaseFactory.obtainDatabase(applicationContext)!!.userDao()
    }

    private fun initFAB() {
        findViewById<FloatingActionButton>(R.id.logFAB).setOnClickListener {
            handleLoginAction()
        }
    }

    private fun handleLoginAction() {
        try {
            validateFields()
            val user = createNewUserFromDataFields()

            GlobalScope.launch(Dispatchers.IO) {
                logIn(this, user)
            }

        } catch (e: ValidationException) {
            handleValidationException(e)

        } catch (e: SQLException) {
            handleSQLException(e)
        }
    }

    private fun logIn(coroutineScope: CoroutineScope, user: User) {
        try {
            val userFromDB = obtainUserFromDatabase(user)

            coroutineScope.launch(Dispatchers.Main) {
                launchTestActivity(userFromDB)
            }

        } catch (e: SQLException) {
            coroutineScope.launch(Dispatchers.Main) {
                handleSQLException(e)
            }
        }
    }

    private fun handleValidationException(e: ValidationException) {
        AlertDialogHelper.showAlertDialog(this, e)
    }

    private fun handleSQLException(e: SQLException) {
        AlertDialogHelper.showAlertDialog(
            this,
            "Error during accessing database",
            "Database Error"
        )
        Log.e("DB_ACCESS", e.message.toString())
    }

    private fun obtainUserFromDatabase(user: User): User {
        var obtainedUser = userDao.getUser(user.name, user.surname, user.age)
        if (obtainedUser == null) {
            userDao.insertNewUser(user)
            obtainedUser = userDao.getUser(user.name, user.surname, user.age)
        }
        return obtainedUser!!
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
                launchLogAsCaretakerActivity()
                true
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