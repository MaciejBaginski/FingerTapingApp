package com.example.fingertapingapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fingertapingapp.entities.Attempt

class AttemptsAdapter(private val dataSet: List<Attempt>) : RecyclerView.Adapter<AttemptsAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val resultField : TextView = view.findViewById(R.id.resultTextField)
        private val dateField : TextView = view.findViewById(R.id.dateTextField)
        private val nameField : TextView = view.findViewById(R.id.nameTextField)

        fun prepareDateToDisplay(attempt: Attempt) {
            resultField.text = attempt.result.toString()
            dateField.text = attempt.getDatetimeAsFormattedString()
            nameField.text = attempt.user.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.attempt_recycler_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.prepareDateToDisplay(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}