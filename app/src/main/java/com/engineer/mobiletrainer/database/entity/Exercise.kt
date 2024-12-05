package com.engineer.mobiletrainer.database.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "exercises", indices = [Index(value = ["name"], unique = true)])
data class Exercise (
    @ColumnInfo(name = "name") val name: String?
) : Parcelable{
    @PrimaryKey(autoGenerate = true) var eid: Int = 0
    @ColumnInfo(name = "description") var desc: String = ""

    constructor(parcel: Parcel) : this(parcel.readString()) {
        eid = parcel.readInt()
        desc = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(eid)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercise> {
        override fun createFromParcel(parcel: Parcel): Exercise {
            return Exercise(parcel)
        }

        override fun newArray(size: Int): Array<Exercise?> {
            return arrayOfNulls(size)
        }
    }
}