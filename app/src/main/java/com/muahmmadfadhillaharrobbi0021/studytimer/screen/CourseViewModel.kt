package com.muahmmadfadhillaharrobbi0021.studytimer.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muahmmadfadhillaharrobbi0021.studytimer.database.CourseDao
import com.muahmmadfadhillaharrobbi0021.studytimer.model.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CourseViewModel(private val dao: CourseDao) : ViewModel() {

    val courses: StateFlow<List<Course>> = dao.getCourses().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

    fun insert(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            val existing = dao.getCourseByName(name)
            if (existing == null) {
                dao.insert(Course(name = name))
            }
        }
    }

    fun delete(course: Course) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(course)
        }
    }
}