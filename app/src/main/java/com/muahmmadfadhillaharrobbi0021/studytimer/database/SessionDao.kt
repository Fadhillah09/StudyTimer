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

    @Query("SELECT * FROM sessions WHERE isDeleted = 0 ORDER BY timestamp DESC")
    fun getSessions(): Flow<List<Session>>

    @Query("UPDATE sessions SET isDeleted = 1 WHERE id = :id")
    suspend fun moveToRecycleBin(id: Long)

    @Query("UPDATE sessions SET isDeleted = 0 WHERE id = :id")
    suspend fun restoreFromRecycleBin(id: Long)

    @Query("DELETE FROM sessions WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM sessions WHERE isDeleted = 1 ORDER BY timestamp DESC")
    fun getRecycleBin(): Flow<List<Session>>

    @Query("DELETE FROM sessions WHERE isDeleted = 1")
    suspend fun emptyRecycleBin()

}