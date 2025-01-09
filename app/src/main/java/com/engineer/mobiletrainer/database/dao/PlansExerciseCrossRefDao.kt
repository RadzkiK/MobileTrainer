package com.engineer.mobiletrainer.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef

@Dao
interface PlansExerciseCrossRefDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(plansExerciseCrossRef: PlansExerciseCrossRef)

    @Delete
    suspend fun delete(plansExerciseCrossRef: PlansExerciseCrossRef)

}