package com.engineer.mobiletrainer.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings WHERE sid == :sid")
    fun getSettingWitID(sid : Int) : Settings

    @Query("SELECT value FROM settings WHERE setting == :name")
    fun getSettingValueOf(name : String) : Int
    @Query("SELECT * FROM settings")
    fun getAll() : List<Settings>
    @Update
    fun updateSetting(setting : Settings)
    @Insert
    fun insertSetting(setting : Settings)
}