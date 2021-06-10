package com.example.fingertapingapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable
import java.math.RoundingMode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
data class Attempt(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "datetime") val datetime: LocalDateTime,
    @ColumnInfo(name = "result") val result: Int,
    @ColumnInfo(name = "userId") val userId: Long
) : Serializable {

    @Ignore
    private val testDurationInSeconds: Int = 30

    constructor(datetime: LocalDateTime, result: Int, userId: Long) : this(
        0,
        datetime,
        result,
        userId
    )

    fun getDatetimeAsFormattedString(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd  hh:mm:ss ")
        return datetime.format(formatter)
    }

    private fun getAverageBreakBetweenInMilliseconds(): Int {
        return testDurationInSeconds * 1000 / result
    }

    private fun getFrequency(): Double {
        return (result / testDurationInSeconds.toDouble()).toBigDecimal()
            .setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

    fun getResultAsFormattedString(): String {
        return "$result taps\n" +
                "avg. interval ${getAverageBreakBetweenInMilliseconds()} ms\n" +
                "${getFrequency()} taps/s"
    }

}