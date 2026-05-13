package com.muahmmadfadhillaharrobbi0021.studytimer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Delete
import com.muahmmadfadhillaharrobbi0021.studytimer.model.Course
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {

    @Insert
    suspend fun insert(course: Course)

    @Delete
    suspend fun delete(course: Course)

    @Query("SELECT * FROM courses ORDER BY name ASC")
    fun getCourses(): Flow<List<Course>>

    @Query("SELECT * FROM courses WHERE name = :name LIMIT 1")
    suspend fun getCourseByName(name: String): Course?
}