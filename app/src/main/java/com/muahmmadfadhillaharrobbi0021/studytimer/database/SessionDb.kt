package com.muahmmadfadhillaharrobbi0021.studytimer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muahmmadfadhillaharrobbi0021.studytimer.model.Session

@Database(entities = [Session::class], version = 1, exportSchema = false)
abstract class SessionDb : RoomDatabase() {

    abstract val dao: SessionDao

    companion object {
        @Volatile
        private var INSTANCE: SessionDb? = null

        fun getInstance(context: Context): SessionDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SessionDb::class.java,
                        "session.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}