package com.example.fingertapingapp.sqlliteutil.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fingertapingapp.entities.Attempt
import com.example.fingertapingapp.entities.Caretaker
import com.example.fingertapingapp.entities.User
import com.example.fingertapingapp.sqlliteutil.converter.LocalDateTimeConverters
import com.example.fingertapingapp.sqlliteutil.dao.AttemptDAO
import com.example.fingertapingapp.sqlliteutil.dao.CaretakerDAO
import com.example.fingertapingapp.sqlliteutil.dao.UserDAO

@Database(entities = [User::class, Caretaker::class, Attempt::class], version = 1)
@TypeConverters(LocalDateTimeConverters::class)
abstract class Database : RoomDatabase() {
    abstract fun userDao(): UserDAO
    abstract fun caretakerDao(): CaretakerDAO
    abstract fun attemptDao(): AttemptDAO
}