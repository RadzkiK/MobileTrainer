package com.engineer.mobiletrainer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "training_sessions")
data class TrainingSession(
    @ColumnInfo(name = "pid") val pid: Int?,
    @ColumnInfo(name = "plan_name") val planName: String?,
    @ColumnInfo(name = "date") val date: Long?
) {
    @PrimaryKey(autoGenerate = true) var tsid: Int = 0
    @ColumnInfo(name = "is_completed") var completed: Boolean = false
}