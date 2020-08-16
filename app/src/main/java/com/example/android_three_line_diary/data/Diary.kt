package com.example.android_three_line_diary.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "diary_table")
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Date,
    val badContent: String,
    val goodContext: String,
    val goalContext: String,
    var deleted: Boolean = false
)