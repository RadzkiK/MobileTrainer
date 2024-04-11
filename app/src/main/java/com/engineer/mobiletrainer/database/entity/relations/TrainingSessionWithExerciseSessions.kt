package com.engineer.mobiletrainer.database.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.engineer.mobiletrainer.database.entity.ExerciseSession
import com.engineer.mobiletrainer.database.entity.TrainingSession

data class TrainingSessionWithExerciseSessions(
    @Embedded val trainingSession: TrainingSession,
    @Relation(
        parentColumn = "tsid",
        entityColumn = "tsid"
    )
    val exerciseSessions: List<ExerciseSession>
)