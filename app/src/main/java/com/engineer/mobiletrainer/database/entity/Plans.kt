package com.engineer.mobiletrainer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "plans", indices = [Index(value = ["name"], unique = true)])
data class Plans (
   @ColumnInfo(name = "name") val name: String?
) {
    @PrimaryKey(autoGenerate = true) var pid: Int = 0
    @ColumnInfo(name = "description") var desc: String = ""
}