package com.engineer.mobiletrainer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class Settings(
    @ColumnInfo(name = "setting") val setting: String?,
    @ColumnInfo(name = "value") val value: Int?
) {
    @PrimaryKey(autoGenerate = true) var sid: Int = 0
    fun getValueOfSetting(name : String): Int? {
        if(name == setting) return value
        else return 0
    }
}