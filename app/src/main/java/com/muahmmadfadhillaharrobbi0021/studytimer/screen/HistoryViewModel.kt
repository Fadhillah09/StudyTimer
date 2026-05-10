package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muahmmadfadhillaharrobbi0021.studytimer.database.SessionDao
import com.muahmmadfadhillaharrobbi0021.studytimer.model.Session
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HistoryViewModel(private val dao: SessionDao) : ViewModel() {

    val sessions: StateFlow<List<Session>> = dao.getSessions().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun insert(
        courseName: String,
        mode: String,
        concentration: String,
        duration: String,
        category: String,
        timestamp: String
    ) {
        val session = Session(
            courseName = courseName,
            mode = mode,
            concentration = concentration,
            duration = duration,
            category = category,
            timestamp = timestamp
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(session)
        }
    }

    fun getSessionById(id: Long): Session? {
        return sessions.value.find { it.id == id }
    }

    fun update(
        id: Long,
        courseName: String,
        mode: String,
        concentration: String,
        duration: String,
        category: String,
        timestamp: String
    ) {
        val session = Session(
            id = id,
            courseName = courseName,
            mode = mode,
            concentration = concentration,
            duration = duration,
            category = category,
            timestamp = timestamp
        )
        viewModelScope.launch(Dispatchers.IO) {
            dao.update(session)
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteAll()
        }
    }
}