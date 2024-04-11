package com.engineer.mobiletrainer.database.entity.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.Profile

data class ProfileWithPlans(
    @Embedded val profile: Profile,
    @Relation(
        parentColumn = "uid",
        entityColumn = "uid"
    )
    val plans: List<Plans>
)