package com.example.fingertapingapp.adapter

import com.example.fingertapingapp.entities.Attempt
import com.example.fingertapingapp.entities.User
import com.example.fingertapingapp.entities.UserWithAttempts

class AttemptAdapterItem private constructor(val attempt: Attempt, val user: User) {
    companion object {
        operator fun invoke(userWithAttempts: UserWithAttempts): List<AttemptAdapterItem> {
            val list = mutableListOf<AttemptAdapterItem>()
            for (attempt: Attempt in userWithAttempts.attempts) {
                list.add(AttemptAdapterItem(attempt, userWithAttempts.user))
            }
            return list
        }

        operator fun invoke(usersWithAttempts: List<UserWithAttempts>): List<AttemptAdapterItem> {
            val list = mutableListOf<AttemptAdapterItem>()
            for (userWithAttempts: UserWithAttempts in usersWithAttempts) {
                list.addAll(AttemptAdapterItem(userWithAttempts))
            }
            return list
        }
    }
}
