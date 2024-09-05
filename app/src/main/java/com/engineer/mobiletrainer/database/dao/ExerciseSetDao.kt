package com.engineer.mobiletrainer.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.engineer.mobiletrainer.database.entity.ExerciseSet
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseSetDao {
    @Query("SELECT * FROM exercise_sets ORDER BY esetid ASC")
    fun getAll(): Flow<MutableList<ExerciseSet>>
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun update(exerciseSet: ExerciseSet)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseSet: ExerciseSet)
    @Delete
    suspend fun delete(exerciseSet: ExerciseSet)
}