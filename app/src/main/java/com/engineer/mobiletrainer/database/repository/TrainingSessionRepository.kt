package com.engineer.mobiletrainer.database.repository

import androidx.annotation.WorkerThread
import com.engineer.mobiletrainer.database.dao.TrainingSessionDao
import com.engineer.mobiletrainer.database.entity.TrainingSession
import com.engineer.mobiletrainer.database.entity.relations.TrainingSessionWithExerciseSessions
import kotlinx.coroutines.flow.Flow

class TrainingSessionRepository(private val trainingSessionDao: TrainingSessionDao) {

    val allTrainingSessions: Flow<List<TrainingSession>> = trainingSessionDao.getAll()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(trainingSession: TrainingSession) {
        trainingSessionDao.insert(trainingSession)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(trainingSession: TrainingSession) {
        trainingSessionDao.update(trainingSession)
    }

    @WorkerThread
    suspend fun delete(trainingSession: TrainingSession) {
        trainingSessionDao.delete(trainingSession)
    }

    @WorkerThread
    suspend fun getTrainingSessionWithExercisesSessions():List<TrainingSessionWithExerciseSessions> {
        return trainingSessionDao.getTrainingSessionWithExercisesSessions()
    }
}