package com.example.fingertapingapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Long,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "surname") val surname: String,
    @ColumnInfo(name = "age") val age: Int
) : Serializable {

    constructor(name: String, surname: String, age: Int) : this(0, name, surname, age)

    override fun toString(): String {
        return "${capitalize(name)} ${capitalize(surname)} ($age y.o.)"
    }

    private fun capitalize(string: String): String {
        return string.replaceFirstChar {
            if (it.isLowerCase()) {
                it.titlecase(Locale.getDefault())
            } else {
                it.toString()
            }
        }
    }
}