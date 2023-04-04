package com.example.roomexample

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "notes")
data class User(
    //here i have used the primerykey because it helps in identifing everyfield uniquely and This helps ensure data integrity and consistency in the database.
    @PrimaryKey
    @ColumnInfo(name = "dateAdded")
    val dateAdded: Date,
    @ColumnInfo(name = "noteText")
    val noteText: String,

    @ColumnInfo(name = "emailText")
    val emailText: String,

    @ColumnInfo(name = "gender")
    val genderText: String,

    @ColumnInfo(name = "passyear")
    val passyearText: String,

    @ColumnInfo(name = "dobdate")
    val dobdateText: String,

    @ColumnInfo(name = "datetime")
    val datetimeText: String,

    @ColumnInfo(name = "startext")
    val starText: String,

)