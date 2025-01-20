package com.engineer.mobiletrainer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.checkerframework.common.aliasing.qual.Unique
import org.jetbrains.annotations.NotNull

@Entity(tableName = "settings", indices = [Index(value = ["setting"], unique = true)])
data class Settings(
    @ColumnInfo(name = "setting") val setting: String?,
    @ColumnInfo(name = "value") val value: Int?
) {
    @PrimaryKey(autoGenerate = true) var sid: Int = 0
}