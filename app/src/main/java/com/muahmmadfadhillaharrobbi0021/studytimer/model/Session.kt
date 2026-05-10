package com.muahmmadfadhillaharrobbi0021.studytimer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class Session(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val courseName: String,
    val mode: String,
    val concentration: String,
    val duration: String,
    val category: String,
    val timestamp: String
)