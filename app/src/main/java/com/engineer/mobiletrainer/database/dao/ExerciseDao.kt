package com.engineer.mobiletrainer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.relations.ExerciseWithSessions
import com.engineer.mobiletrainer.database.entity.relations.ExerciseWithPlans
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM exercises ORDER BY name ASC")
    fun getAll(): Flow<MutableList<Exercise>>
    @Query("SELECT * FROM exercises WHERE name = :named")
    suspend fun getExercise(named: String): Exercise
    @Query("SELECT * FROM exercises ORDER BY name ASC")
    suspend fun getExercises(): MutableList<Exercise>
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(exercise: Exercise)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exercise: Exercise)
    @Query("DELETE FROM exercises WHERE name = :name")
    suspend fun deleteExercise(name: String)

    @Transaction
    @Query("SELECT * FROM exercises")
    suspend fun getExercisesWithSessions(): MutableList<ExerciseWithSessions>

    @Transaction
    @Query("SELECT * FROM exercises JOIN plansexercisecrossref ON exercises.eid = plansexercisecrossref.eid WHERE plansexercisecrossref.eid = :eid")
    suspend fun getExerciseWithPlans(eid: Int): MutableList<ExerciseWithPlans>

}