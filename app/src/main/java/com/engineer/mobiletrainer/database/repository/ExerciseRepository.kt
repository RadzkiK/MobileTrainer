package com.engineer.mobiletrainer.database.repository

import androidx.annotation.WorkerThread
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.dao.ExerciseDao
import com.engineer.mobiletrainer.database.entity.relations.ExerciseWithSessions
import com.engineer.mobiletrainer.database.entity.relations.ExerciseWithPlans
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

    val allExercises: Flow<MutableList<Exercise>> = exerciseDao.getAll()

    @WorkerThread
    suspend fun getExercise(name: String): Exercise {
        return exerciseDao.getExercise(name)
    }

    @WorkerThread
    suspend fun getExercises(): MutableList<Exercise> {
        return exerciseDao.getExercises()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(exercise: Exercise) {
        exerciseDao.update(exercise)
    }

    @WorkerThread
    suspend fun delete(name: String) {
        exerciseDao.deleteExercise(name)
    }

    @WorkerThread
    suspend fun getExercisesWithSessions(): MutableList<ExerciseWithSessions> {
        return exerciseDao.getExercisesWithSessions()
    }

    @WorkerThread
    suspend fun getExerciseWithPlans(eid: Int): MutableList<ExerciseWithPlans> {
        return exerciseDao.getExerciseWithPlans(eid)
    }
}