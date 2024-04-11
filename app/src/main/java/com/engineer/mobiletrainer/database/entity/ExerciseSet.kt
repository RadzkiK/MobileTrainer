package com.engineer.mobiletrainer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_sets")
data class ExerciseSet(
    @ColumnInfo(name = "esid") val esid: Int?,
) {
    @PrimaryKey(autoGenerate = true) var esetid: Int = 0
    @ColumnInfo(name = "reps") var reps: Int? = null
    @ColumnInfo(name = "weight") var weight: Int? = null
    @ColumnInfo(name = "time") var time: Int? = null
}