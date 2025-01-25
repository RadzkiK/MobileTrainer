package com.engineer.mobiletrainer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.engineer.mobiletrainer.database.entity.Settings
import kotlinx.coroutines.flow.Flow

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings WHERE setting == :name")
    suspend fun getSettingNamed(name : String) : Settings
    @Query("SELECT * FROM settings ORDER BY sid")
    fun getAll() : Flow<List<Settings>>
    @Update
    suspend fun updateSetting(setting : Settings)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting : Settings)
}