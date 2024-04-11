package com.engineer.mobiletrainer.database.repository

import androidx.annotation.WorkerThread
import com.engineer.mobiletrainer.database.dao.ExerciseSessionDao
import com.engineer.mobiletrainer.database.entity.ExerciseSession
import com.engineer.mobiletrainer.database.entity.relations.ExerciseSessionWithSets
import kotlinx.coroutines.flow.Flow

class ExerciseSessionRepository(private val exerciseSessionDao: ExerciseSessionDao) {
    val allExerciseSessions: Flow<List<ExerciseSession>> = exerciseSessionDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(exerciseSession: ExerciseSession) {
        exerciseSessionDao.insert(exerciseSession)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(exerciseSession: ExerciseSession) {
        exerciseSessionDao.update(exerciseSession)
    }

    @WorkerThread
    suspend fun delete(exerciseSession: ExerciseSession) {
        exerciseSessionDao.delete(exerciseSession)
    }

    @WorkerThread
    suspend fun getExerciseSessionWithSets(): List<ExerciseSessionWithSets> {
        return exerciseSessionDao.getExerciseSessionWithSets()
    }
}