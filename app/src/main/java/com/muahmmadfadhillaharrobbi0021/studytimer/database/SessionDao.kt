package com.muahmmadfadhillaharrobbi0021.studytimer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.muahmmadfadhillaharrobbi0021.studytimer.model.Session
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {

    @Insert
    suspend fun insert(session: Session)

    @Update
    suspend fun update(session: Session)

    @Query("SELECT * FROM sessions ORDER BY timestamp DESC")
    fun getSessions(): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE id = :id LIMIT 1")
    suspend fun getSessionById(id: Long): Session?

    @Query("DELETE FROM sessions WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM sessions")
    suspend fun deleteAll()

}