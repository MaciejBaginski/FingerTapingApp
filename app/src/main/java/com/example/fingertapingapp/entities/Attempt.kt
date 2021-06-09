package com.example.fingertapingapp.entities

import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalQueries.localDate


data class Attempt(private val _datetime: LocalDateTime, val result: Int) : Serializable {

    private var id: Long = -1
    var user: User? = null

    constructor(datetime: LocalDateTime, result: Int, id: Long) : this(datetime, result) {
        this.id = id;
    }

    fun getDatetimeAsFormattedString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm:ss ")
        return _datetime.format(formatter)
    }

}