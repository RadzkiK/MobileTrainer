package com.engineer.mobiletrainer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "exercises", indices = [Index(value = ["name"], unique = true)])
data class Exercise (
    @ColumnInfo(name = "name") val name: String?
) {
    @PrimaryKey(autoGenerate = true) var eid: Int = 0
    @ColumnInfo(name = "description") var desc: String = ""
}