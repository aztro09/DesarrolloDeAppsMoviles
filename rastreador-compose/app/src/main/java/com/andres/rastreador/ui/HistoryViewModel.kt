
package com.andres.rastreador.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.andres.rastreador.data.AppDatabase
import com.andres.rastreador.data.LocationRepository
import kotlinx.coroutines.launch

class HistoryViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = LocationRepository(AppDatabase.get(app).locationDao())
    fun clear() = viewModelScope.launch { repo.clear() }
}
