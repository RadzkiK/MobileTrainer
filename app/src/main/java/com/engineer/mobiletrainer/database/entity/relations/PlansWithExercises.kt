package com.engineer.mobiletrainer.database.entity.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef

data class PlansWithExercises(
    @Embedded val plan: Plans,
    @Relation(
        parentColumn = "pid",
        entityColumn = "eid",
        associateBy = Junction(PlansExerciseCrossRef::class)
    )
    val exercises: List<Exercise>
)

data class ExercisesWithPlans(
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "eid",
        entityColumn = "pid",
        associateBy = Junction(PlansExerciseCrossRef::class)
    )
    val plans: List<Plans>
)