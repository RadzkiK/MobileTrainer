package com.engineer.mobiletrainer.database.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "plans", indices = [Index(value = ["name"], unique = true)])
data class Plans (
   @ColumnInfo(name = "name") val name: String?
) : Parcelable {
    @PrimaryKey(autoGenerate = true) var pid: Int = 0
    @ColumnInfo(name = "description") var desc: String = ""

    constructor(parcel: Parcel) : this(parcel.readString()) {
        pid = parcel.readInt()
        desc = parcel.readString()!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeInt(pid)
        parcel.writeString(desc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Plans> {
        override fun createFromParcel(parcel: Parcel): Plans {
            return Plans(parcel)
        }

        override fun newArray(size: Int): Array<Plans?> {
            return arrayOfNulls(size)
        }
    }
}