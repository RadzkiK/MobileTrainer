package com.engineer.mobiletrainer.database.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.TrainingSession

data class PlansWithTrainingSessions(
    @Embedded val plans: Plans,
    @Relation(
        parentColumn = "pid",
        entityColumn = "pid"
    )
    val trainingSessions: List<TrainingSession>
)