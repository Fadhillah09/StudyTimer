package com.muahmmadfadhillaharrobbi0021.studytimer.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muahmmadfadhillaharrobbi0021.studytimer.database.SessionDb
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.CourseViewModel
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.HistoryViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val db = SessionDb.getInstance(context)
        return when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) ->
                HistoryViewModel(db.dao) as T
            modelClass.isAssignableFrom(CourseViewModel::class.java) ->
                CourseViewModel(db.courseDao) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}