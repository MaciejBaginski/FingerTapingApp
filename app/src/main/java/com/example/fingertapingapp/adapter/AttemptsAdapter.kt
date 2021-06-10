package com.example.fingertapingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fingertapingapp.R
import com.example.fingertapingapp.entities.Attempt
import com.example.fingertapingapp.entities.User
import com.example.fingertapingapp.entities.UserWithAttempts

class AttemptsAdapter(private val userWithAttempts: UserWithAttempts?) :
    RecyclerView.Adapter<AttemptsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val resultField: TextView = view.findViewById(R.id.resultTextField)
        private val dateField: TextView = view.findViewById(R.id.dateTextField)
        private val nameField: TextView = view.findViewById(R.id.nameTextField)

        fun prepareDateToDisplay(attempt: Attempt, user: User) {
            resultField.text = attempt.getResultAsFormattedString()
            dateField.text = attempt.getDatetimeAsFormattedString()
            nameField.text = user.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.attempt_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (userWithAttempts != null) {
            holder.prepareDateToDisplay(userWithAttempts.attempts[position], userWithAttempts.user)
        }
    }

    override fun getItemCount(): Int {
        return userWithAttempts?.attempts?.size ?: 0
    }
}