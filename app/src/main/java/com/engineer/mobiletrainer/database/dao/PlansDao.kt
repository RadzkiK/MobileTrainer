package com.engineer.mobiletrainer.database.dao

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.database.entity.relations.PlanWithExercises
import com.engineer.mobiletrainer.database.entity.relations.PlansWithTrainingSessions
import kotlinx.coroutines.flow.Flow

@Dao
interface PlansDao {

    @Query("SELECT * FROM plans ORDER BY name ASC")
    fun getAll(): Flow<MutableList<Plans>>

    @Query("SELECT * FROM plans WHERE name = :named")
    suspend fun getPlan(named: String): Plans
    @Query("SELECT * FROM PLANS ORDER BY name ASC")
    suspend fun getPlans(): MutableList<Plans>
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(plan: Plans)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: Plans)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertPlanExercise(plansExerciseCrossRef: PlansExerciseCrossRef)

    @Query("DELETE FROM plans WHERE name = :name")
    suspend fun deletePlan(name: String)

    @Transaction
    @Query("SELECT * FROM plans")
    suspend fun getPlansWithTrainingSessions(): MutableList<PlansWithTrainingSessions>

    @Transaction
    @Query("SELECT * FROM plans JOIN plansexercisecrossref ON plans.pid = plansexercisecrossref.pid WHERE plansexercisecrossref.pid = :pid")
    fun getPlanWithExercises(pid: Int): Flow<MutableList<PlanWithExercises>>

    @Transaction
    @Query("SELECT * FROM plansexercisecrossref WHERE plansexercisecrossref.eid = :eid AND plansexercisecrossref.pid = :pid")
    fun getPlanExerciseCrossRef(eid: Int, pid: Int): Flow<PlansExerciseCrossRef>

    @Transaction
    @Query("SELECT * FROM plansexercisecrossref WHERE plansexercisecrossref.eid = :eid AND plansexercisecrossref.pid = :pid")
    suspend fun getPlanExerciseCrossRef2(eid: Int, pid: Int): PlansExerciseCrossRef

}