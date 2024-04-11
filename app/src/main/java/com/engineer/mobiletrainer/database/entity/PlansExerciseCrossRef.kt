package com.engineer.mobiletrainer.database.entity

import androidx.room.Entity

@Entity(primaryKeys = ["pid", "eid"])
data class PlansExerciseCrossRef(
    val pid: Int,
    val eid: Int
)