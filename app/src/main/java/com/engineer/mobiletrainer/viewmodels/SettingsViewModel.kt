package com.engineer.mobiletrainer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.engineer.mobiletrainer.database.Settings
import com.engineer.mobiletrainer.database.SettingsRepository
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {
    val allSettings: LiveData<List<Settings>> = repository.allSettings.asLiveData()

    fun insert(settings: Settings) = viewModelScope.launch {
        repository.insert(settings)
    }

    fun update(settings: Settings) = viewModelScope.launch {
        repository.update(settings)
    }

    fun getValueOfSettingNamed(name: String) = viewModelScope.launch {
        repository.getValueOfSettingNamed(name)
    }

    fun getAllSettings() : List<Settings>? {
        return allSettings.value
    }
}

class SettingsViewModelFactory(private val repository: SettingsRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SettingsViewModel::class.java))
            return SettingsViewModel(repository) as T

        throw IllegalArgumentException("Unknown Class for View Model")
    }

}