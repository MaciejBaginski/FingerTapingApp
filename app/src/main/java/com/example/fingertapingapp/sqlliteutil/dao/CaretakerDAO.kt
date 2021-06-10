package com.example.fingertapingapp.sqlliteutil.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.fingertapingapp.entities.Caretaker

@Dao
interface CaretakerDAO {
    @Query("SELECT * FROM caretaker WHERE login = :login AND password = :password")
    fun getCaretaker(login: String, password: String): Caretaker?
}