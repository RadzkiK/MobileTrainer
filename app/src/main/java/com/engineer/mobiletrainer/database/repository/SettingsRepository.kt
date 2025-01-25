package com.engineer.mobiletrainer.database.repository

import androidx.annotation.WorkerThread
import com.engineer.mobiletrainer.database.entity.Settings
import com.engineer.mobiletrainer.database.dao.SettingsDao
import kotlinx.coroutines.flow.Flow

class SettingsRepository(private val settingsDao: SettingsDao) {
    val allSettings: Flow<List<Settings>> = settingsDao.getAll()

    @WorkerThread
    suspend fun getSettingNamed(name: String): Settings {
        return settingsDao.getSettingNamed(name)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(settings: Settings) {
        settingsDao.insertSetting(settings)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(settings: Settings) {
        settingsDao.updateSetting(settings)
    }
}