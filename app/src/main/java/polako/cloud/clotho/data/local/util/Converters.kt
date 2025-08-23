package polako.cloud.clotho.data.local.util

import androidx.room.TypeConverter
import java.time.Duration
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * Type converters for Room database to handle non-primitive types
 */
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? =
        value?.let {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
        }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? = date?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()

    @TypeConverter
    fun fromDurationMillis(value: Long?): Duration? = value?.let { Duration.ofMillis(it) }

    @TypeConverter
    fun durationToMillis(duration: Duration?): Long? = duration?.toMillis()

    @TypeConverter
    fun fromStringList(list: List<String>?): String? = list?.joinToString(",")

    @TypeConverter
    fun toStringList(data: String?): List<String> = data?.split(",") ?: emptyList()
}
