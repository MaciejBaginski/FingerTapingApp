package com.example.fingertapingapp.sqlliteutil.dao

import androidx.room.*
import com.example.fingertapingapp.entities.User
import com.example.fingertapingapp.entities.UserWithAttempts

@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNewUser(user: User)

    @Query("SELECT * FROM user WHERE userId = :userId")
    @Transaction
    fun getUserWithAttempts(userId: Long): UserWithAttempts?

    @Query("SELECT * FROM user")
    @Transaction
    fun getUsersWithAttempts(): List<UserWithAttempts>?

    @Query("SELECT * FROM user WHERE age = :age AND name = :name AND surname = :surname")
    fun getUser(name: String, surname: String, age: Int): User?

}