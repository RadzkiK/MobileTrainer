package com.engineer.mobiletrainer.database.repository

import androidx.annotation.WorkerThread
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.dao.PlansDao
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.database.entity.relations.PlansWithExercises
import com.engineer.mobiletrainer.database.entity.relations.PlansWithTrainingSessions
import kotlinx.coroutines.flow.Flow

class PlansRepository(private val plansDao: PlansDao) {
    val allPlans: Flow<MutableList<Plans>> = plansDao.getAll()

    @WorkerThread
    suspend fun getPlan(name: String): Plans {
        return plansDao.getPlan(name)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(plan: Plans) {
        plansDao.insert(plan)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPlanExercise(plansExerciseCrossRef: PlansExerciseCrossRef) {
        plansDao.insertPlanExercise(plansExerciseCrossRef)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(plan: Plans) {
        plansDao.update(plan)
    }

    @WorkerThread
    suspend fun delete(name: String) {
        plansDao.deletePlan(name)
    }

    @WorkerThread
    suspend fun getPlansWithTrainingSessions(): MutableList<PlansWithTrainingSessions> {
        return plansDao.getPlansWithTrainingSessions()
    }

    @WorkerThread
    suspend fun getPlansWithExercises(): MutableList<PlansWithExercises> {
        return plansDao.getPlansWithExercises()
    }
}