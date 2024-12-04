package com.engineer.mobiletrainer.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.engineer.mobiletrainer.database.entity.ExerciseSession
import com.engineer.mobiletrainer.database.entity.relations.ExerciseSessionWithSets
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseSessionDao {
    @Query("SELECT * FROM exercise_sessions ORDER BY esid ASC")
    fun getAll(): Flow<MutableList<ExerciseSession>>
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(exerciseSession: ExerciseSession)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseSession: ExerciseSession)
    @Delete
    suspend fun delete(exerciseSession: ExerciseSession)

    @Transaction
    @Query("SELECT * FROM exercise_sessions")
    suspend fun getExerciseSessionWithSets(): MutableList<ExerciseSessionWithSets>
}