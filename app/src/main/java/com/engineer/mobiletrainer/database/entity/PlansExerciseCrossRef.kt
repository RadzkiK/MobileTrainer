package com.engineer.mobiletrainer.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class PlansExerciseCrossRef(
    val pid: Int,
    val eid: Int
) {
    @PrimaryKey(autoGenerate = true) var peid: Int = 0
}