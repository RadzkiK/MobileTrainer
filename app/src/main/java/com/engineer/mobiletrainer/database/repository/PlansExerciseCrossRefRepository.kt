package com.engineer.mobiletrainer.database.repository

import androidx.annotation.WorkerThread
import com.engineer.mobiletrainer.database.dao.PlansExerciseCrossRefDao
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef

class PlansExerciseCrossRefRepository(private val plansExerciseCrossRefDao: PlansExerciseCrossRefDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(plansExerciseCrossRef: PlansExerciseCrossRef) {
        plansExerciseCrossRefDao.insert(plansExerciseCrossRef)
    }

    @WorkerThread
    suspend fun delete(plansExerciseCrossRef: PlansExerciseCrossRef) {
        plansExerciseCrossRefDao.delete(plansExerciseCrossRef)
    }
}