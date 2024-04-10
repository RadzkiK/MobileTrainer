package com.engineer.mobiletrainer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.engineer.mobiletrainer.database.Profile
import com.engineer.mobiletrainer.database.ProfileRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {
    val allProfiles: LiveData<List<Profile>> = profileRepository.allProfile.asLiveData()

    private var profile: Profile = Profile("none", "none")
        get() = field

    fun getProfileOf(named: String) = viewModelScope.launch {
        profile = profileRepository.getProfileOf(named = named)
    }

    fun insert(profile: Profile) = viewModelScope.launch {
        profileRepository.insert(profile = profile)
    }

    fun update(profile: Profile) = viewModelScope.launch {
        profileRepository.update(profile = profile)
    }

    fun delete(name: String, surname: String) = viewModelScope.launch {
        profileRepository.delete(name = name, surname = surname)
    }

    fun getAllProfiles() : List<Profile>? {
        return allProfiles.value
    }

}

class ProfileViewModelFactory(private val repository: ProfileRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProfileViewModel::class.java))
            return ProfileViewModel(repository) as T

        throw IllegalArgumentException("Unknown Class for View Model")
    }

}