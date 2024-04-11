package com.engineer.mobiletrainer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.database.entity.relations.PlansWithExercises
import com.engineer.mobiletrainer.database.entity.relations.PlansWithTrainingSessions
import kotlinx.coroutines.flow.Flow

@Dao
interface PlansDao {

    @Query("SELECT * FROM plans ORDER BY name ASC")
    fun getAll(): Flow<List<Plans>>

    @Query("SELECT * FROM plans WHERE name = :named")
    fun getPlan(named: String): Plans
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(plan: Plans)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(plan: Plans)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanExercise(plansExerciseCrossRef: PlansExerciseCrossRef)

    @Query("DELETE FROM plans WHERE name = :name")
    suspend fun deletePlan(name: String)

    @Transaction
    @Query("SELECT * FROM plans")
    fun getPlansWithTrainingSessions(): List<PlansWithTrainingSessions>

    @Transaction
    @Query("SELECT * FROM plans")
    fun getPlansWithExercises(): List<PlansWithExercises>

}