package com.engineer.mobiletrainer

import android.app.Application
import com.engineer.mobiletrainer.database.AppDatabase
import com.engineer.mobiletrainer.database.Profile
import com.engineer.mobiletrainer.database.ProfileRepository
import com.engineer.mobiletrainer.database.SettingsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MobileTrainerApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getInstance(this, applicationScope) }
    val settingsRepository by lazy { SettingsRepository(database.settingsDao()) }
    val profileRepository by lazy { ProfileRepository(database.profileDao())}
}