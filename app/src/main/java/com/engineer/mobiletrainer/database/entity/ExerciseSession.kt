package com.engineer.mobiletrainer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_sessions", indices = [Index(value = ["tsid","eid"], unique = true)])
data class ExerciseSession(
    @ColumnInfo(name = "tsid") val tsid: Int?,
    @ColumnInfo(name = "eid") val eid: Int?
) {
    @PrimaryKey(autoGenerate = true) var esid: Int = 0
}