package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ActivityDao
import com.example.data.model.ActivityItem
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ActivityViewModel(private val activityDao: ActivityDao) : ViewModel() {
    val activities: StateFlow<List<ActivityItem>> = activityDao.getAllActivities()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun markAsRead(id: String) {
        viewModelScope.launch {
            activityDao.markAsRead(id)
        }
    }

    fun deleteActivity(id: String) {
        viewModelScope.launch {
            activityDao.deleteActivity(id)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            activityDao.clearAll()
        }
    }
}
