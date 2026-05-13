package com.muahmmadfadhillaharrobbi0021.studytimer.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "courses")
data class Course(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String
)