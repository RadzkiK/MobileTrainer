package com.engineer.mobiletrainer.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "profile", indices = [Index(value = ["name","surname"], unique = true)])
data class Profile(
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "surname") var surname: String?
) {
    @PrimaryKey(autoGenerate = true) var uid: Int = 0
    @ColumnInfo(name = "height") var height: Int = 0
    @ColumnInfo(name = "weight") var weight: Int = 0
    @ColumnInfo(name = "birth_date") var birthDate: Long = 0
    @ColumnInfo(name = "sex") var sex: Int = 2
    @ColumnInfo(name = "last_plan_id") var pid: Int? = null

}