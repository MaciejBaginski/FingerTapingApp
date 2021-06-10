package com.example.fingertapingapp.sqlliteutil.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.util.*

class LocalDateTimeConverters {
    @TypeConverter
    fun localDateTimeFromTimestamp(value: Long?): LocalDateTime? {
        return value?.let {
            LocalDateTime.ofInstant(
                Instant.ofEpochMilli(value),
                TimeZone.getDefault().toZoneId()
            )
        }
    }

    @TypeConverter
    fun timestampFromLocalDateTime(value: LocalDateTime?): Long? {
        return value?.atZone(TimeZone.getDefault().toZoneId())?.toInstant()?.toEpochMilli()
    }
}