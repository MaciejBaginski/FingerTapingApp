package com.example.fingertapingapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fingertapingapp.AttemptsAdapter
import com.example.fingertapingapp.R
import com.example.fingertapingapp.entities.Attempt


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)
        setSupportActionBar(findViewById(R.id.resultToolbar))
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val dataSet: MutableList<Attempt> = mutableListOf()
        val adapter = AttemptsAdapter(dataSet)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            layoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)

        val attempt: Attempt = intent.getSerializableExtra("NEW_ATTEMPT") as Attempt

        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);
        dataSet.add(attempt);

        adapter.notifyDataSetChanged()
    }
}