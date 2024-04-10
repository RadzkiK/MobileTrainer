package com.engineer.mobiletrainer.database

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class ProfileRepository(private val profileDao: ProfileDao) {
    val allProfile: Flow<List<Profile>> = profileDao.getAll()

    @WorkerThread
    fun getProfileOf(named: String): Profile {
        return profileDao.getProfileOf(named)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(profile: Profile) {
        profileDao.insertProfile(profile)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    @WorkerThread
    suspend fun delete(name: String, surname: String) {
        profileDao.deleteProfileOf(name, surname)
    }

}