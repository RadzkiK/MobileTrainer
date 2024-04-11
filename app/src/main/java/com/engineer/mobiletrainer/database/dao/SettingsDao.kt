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

    @Query("SELECT value FROM settings WHERE setting == :name")
    fun getSettingValueOf(name : String) : Int
    @Query("SELECT * FROM settings ORDER BY sid")
    fun getAll() : Flow<List<Settings>>
    @Update
    suspend fun updateSetting(setting : Settings)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting : Settings)
}