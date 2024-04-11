package com.engineer.mobiletrainer

import android.app.Application
import com.engineer.mobiletrainer.database.AppDatabase
import com.engineer.mobiletrainer.database.repository.ExerciseRepository
import com.engineer.mobiletrainer.database.repository.ExerciseSessionRepository
import com.engineer.mobiletrainer.database.repository.ExerciseSetRepository
import com.engineer.mobiletrainer.database.repository.PlansRepository
import com.engineer.mobiletrainer.database.repository.ProfileRepository
import com.engineer.mobiletrainer.database.repository.SettingsRepository
import com.engineer.mobiletrainer.database.repository.TrainingSessionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MobileTrainerApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getInstance(this, applicationScope) }
    val settingsRepository by lazy { SettingsRepository(database.settingsDao()) }
    val profileRepository by lazy { ProfileRepository(database.profileDao()) }
    val plansRepository by lazy {PlansRepository(database.plansDao())}
    val exerciseRepository by lazy {ExerciseRepository(database.exerciseDao())}
    val trainingSessionRepository by lazy {TrainingSessionRepository(database.trainingSessionDao())}
    val exerciseSessionRepository by lazy {ExerciseSessionRepository(database.exerciseSessionDao())}
    val exerciseSetRepository by lazy {ExerciseSetRepository(database.exercisesSetDao())}
}