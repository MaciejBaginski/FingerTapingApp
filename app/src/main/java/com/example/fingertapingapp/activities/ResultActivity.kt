package com.example.fingertapingapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fingertapingapp.R
import com.example.fingertapingapp.adapter.AttemptsAdapter
import com.example.fingertapingapp.entities.Caretaker
import com.example.fingertapingapp.entities.User
import com.example.fingertapingapp.entities.UserWithAttempts
import com.example.fingertapingapp.sqlliteutil.DatabaseFactory
import com.example.fingertapingapp.sqlliteutil.dao.AttemptDAO
import com.example.fingertapingapp.sqlliteutil.dao.UserDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ResultActivity : AppCompatActivity() {

    private lateinit var attemptsDao: AttemptDAO
    private lateinit var userDao: UserDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        initToolbar()

        initRecyclerView()
        initDAOs()

        populateAttemptsList()
    }

    private fun populateAttemptsList() {
        val userId = obtainUserId()

        GlobalScope.launch(Dispatchers.IO) {
            val userWithAttempts = obtainUserWithAttempts(userId)
            launch(Dispatchers.Main) {
                updateAdapter(userWithAttempts)
            }
        }
    }

    private fun obtainUserId(): Long {
        return (intent.getSerializableExtra("USER") as User?)?.userId
            ?: (intent.getSerializableExtra("CARETAKER") as Caretaker).assignedUserId
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.resultToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun initDAOs() {
        attemptsDao = DatabaseFactory.obtainDatabase(applicationContext)!!.attemptDao()
        userDao = DatabaseFactory.obtainDatabase(applicationContext)!!.userDao()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        val adapter = AttemptsAdapter(null)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    private fun obtainUserWithAttempts(userId: Long): UserWithAttempts {
        return userDao.getUserWithAttempts(userId)!!
    }

    private fun updateAdapter(userWithAttempts: UserWithAttempts) {
        val adapter = AttemptsAdapter(userWithAttempts)
        findViewById<RecyclerView>(R.id.recyclerView).adapter = adapter
        adapter.notifyDataSetChanged()
    }
}