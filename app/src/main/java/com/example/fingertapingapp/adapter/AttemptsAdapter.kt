package com.example.fingertapingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fingertapingapp.R

class AttemptsAdapter(private val attemptAdapterItems: List<AttemptAdapterItem>) :
    RecyclerView.Adapter<AttemptsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val resultField: TextView = view.findViewById(R.id.resultTextField)
        private val dateField: TextView = view.findViewById(R.id.dateTextField)
        private val nameField: TextView = view.findViewById(R.id.nameTextField)

        fun prepareDateToDisplay(attemptAdapterItem: AttemptAdapterItem) {
            resultField.text = attemptAdapterItem.attempt.getResultAsFormattedString()
            dateField.text = attemptAdapterItem.attempt.getDatetimeAsFormattedString()
            nameField.text = attemptAdapterItem.user.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.attempt_recycler_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.prepareDateToDisplay(attemptAdapterItems[position])
    }

    override fun getItemCount(): Int {
        return attemptAdapterItems.size
    }
}