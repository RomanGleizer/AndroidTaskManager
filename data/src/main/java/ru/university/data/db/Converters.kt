package ru.university.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime as JLocalDateTime
import kotlinx.serialization.json.Json

object Converters {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter = DateTimeFormatter.ISO_DATE_TIME

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromString(value: String?): LocalDateTime? =
        value?.let { JLocalDateTime.parse(it, formatter).toKotlinLocalDateTime() }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun localDateTimeToString(date: LocalDateTime?): String? =
        date?.let {
            JLocalDateTime.of(
                it.year, it.monthNumber, it.dayOfMonth,
                it.hour, it.minute, it.second
            ).format(formatter)
        }


    @TypeConverter
    fun fromJsonList(value: String?): List<String> =
        value?.let { Json.decodeFromString(it) } ?: emptyList()

    @TypeConverter
    fun listToJson(value: List<String>?): String =
        value?.let { Json.encodeToString(it) } ?: "[]"
}