package com.example.fingertapingapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Caretaker(
    @PrimaryKey(autoGenerate = true) val caretakerId: Long,
    @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "password") val password: String
) : Serializable