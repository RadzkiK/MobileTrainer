package com.engineer.mobiletrainer.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.engineer.mobiletrainer.database.entity.TrainingSession
import com.engineer.mobiletrainer.database.entity.relations.TrainingSessionWithExerciseSessions
import kotlinx.coroutines.flow.Flow

@Dao
interface TrainingSessionDao {
    @Query("SELECT * FROM training_sessions ORDER BY tsid ASC")
    fun getAll(): Flow<List<TrainingSession>>
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(trainingSession: TrainingSession)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trainingSession: TrainingSession)
    @Delete
    suspend fun delete(trainingSession: TrainingSession)

    @Transaction
    @Query("SELECT * FROM training_sessions")
    fun getTrainingSessionWithExercisesSessions(): List<TrainingSessionWithExerciseSessions>
}