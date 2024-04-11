package com.engineer.mobiletrainer.database.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.engineer.mobiletrainer.database.entity.ExerciseSession
import com.engineer.mobiletrainer.database.entity.ExerciseSet

data class ExerciseSessionWithSets(
    @Embedded val exerciseSession: ExerciseSession,
    @Relation(
        parentColumn = "esid",
        entityColumn = "esid"
    )
    val exerciseSets: List<ExerciseSet>
) {
}