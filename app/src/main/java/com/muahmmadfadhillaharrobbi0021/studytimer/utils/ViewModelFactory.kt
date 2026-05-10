package com.muahmmadfadhillaharrobbi0021.studytimer.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.muahmmadfadhillaharrobbi0021.studytimer.database.SessionDb
import com.muahmmadfadhillaharrobbi0021.studytimer.screen.HistoryViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val dao = SessionDb.getInstance(context).dao
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}