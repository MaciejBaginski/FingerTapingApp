package com.example.fingertapingapp.sqlliteutil

import android.content.Context
import androidx.room.Room
import com.example.fingertapingapp.sqlliteutil.database.Database

class DatabaseFactory {
    companion object {
        var database: Database? = null

        fun obtainDatabase(applicationContext: Context): Database? {
            if (database == null) {
                database = Room.databaseBuilder(
                    applicationContext,
                    Database::class.java, "database"
                ).build()
            }
            return database
        }
    }
}