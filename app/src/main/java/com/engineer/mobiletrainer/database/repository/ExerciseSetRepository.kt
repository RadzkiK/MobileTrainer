package com.engineer.mobiletrainer.database.repository

import androidx.annotation.WorkerThread
import com.engineer.mobiletrainer.database.dao.ExerciseSetDao
import com.engineer.mobiletrainer.database.entity.ExerciseSet
import kotlinx.coroutines.flow.Flow

class ExerciseSetRepository(private val exerciseSetDao: ExerciseSetDao) {
    val allExerciseSets: Flow<List<ExerciseSet>> = exerciseSetDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(exerciseSet: ExerciseSet) {
        exerciseSetDao.insert(exerciseSet)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(exerciseSet: ExerciseSet) {
        exerciseSetDao.update(exerciseSet)
    }

    @WorkerThread
    suspend fun delete(exerciseSet: ExerciseSet) {
        exerciseSetDao.delete(exerciseSet)
    }
}