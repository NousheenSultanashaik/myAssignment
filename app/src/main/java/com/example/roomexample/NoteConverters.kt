package com.example.roomexample

import androidx.room.TypeConverter
import java.util.*

class NoteConverters {
    // This method converts a Date object to a timestamp (Long) value.
// It takes a Date object as input and returns a Long value.
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}