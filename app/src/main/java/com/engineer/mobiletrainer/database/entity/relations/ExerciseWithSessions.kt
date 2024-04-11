package com.engineer.mobiletrainer.database.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.ExerciseSession

data class ExerciseWithSessions(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "eid",
        entityColumn = "eid"
    )
    val sessions: List<ExerciseSession>
)