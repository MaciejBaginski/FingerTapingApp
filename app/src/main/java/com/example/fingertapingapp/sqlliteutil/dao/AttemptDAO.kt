package com.example.fingertapingapp.sqlliteutil.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.fingertapingapp.entities.Attempt

@Dao
interface AttemptDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAttempt(attempt: Attempt)

    @Query("SELECT * FROM attempt WHERE userId = :userId")
    fun getAttemptsForUser(userId: Int): List<Attempt>
}