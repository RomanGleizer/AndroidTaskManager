package ru.university.data.db

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.serialization.json.Json
import kotlinx.datetime.LocalDateTime as KLocalDateTime
import java.time.LocalDateTime as JLocalDateTime

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

    @RequiresApi(Build.VERSION_CODES.O)
    fun KLocalDateTime.toJavaLocalDateTime(): JLocalDateTime =
        JLocalDateTime.of(year, monthNumber, dayOfMonth, hour, minute, second, nanosecond)

    fun String.parseToLocalDateTime(): LocalDateTime =
        try {
            Instant.parse(this).toLocalDateTime(TimeZone.currentSystemDefault())
        } catch (e: Exception) {
            val cleanInput = if (this.endsWith("Z")) this.removeSuffix("Z") else this
            LocalDateTime.parse(cleanInput)
        }

    fun String.parseToLocalDateTimeOrNull(): LocalDateTime? = try {
        Instant.parse(this).toLocalDateTime(TimeZone.currentSystemDefault())
    } catch (e: Exception) {
        val cleanInput = if (this.endsWith("Z")) this.removeSuffix("Z") else this
        try {
            LocalDateTime.parse(cleanInput)
        } catch (ex: Exception) {
            null
        }
    }

    fun String.parseToLocalDateTimeOrDefault(): LocalDateTime =
        parseToLocalDateTimeOrNull() ?: LocalDateTime(1970, 1, 1, 0, 0)

    fun String.toLocalDateTimeSafe(): LocalDateTime? = try {
        LocalDateTime.parse(this)
    } catch (e: Exception) {
        null
    }
}