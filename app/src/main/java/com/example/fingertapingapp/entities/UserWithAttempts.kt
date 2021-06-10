package com.example.fingertapingapp.entities

import androidx.room.Embedded
import androidx.room.Relation

class UserWithAttempts(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )
    val attempts: List<Attempt>
)
