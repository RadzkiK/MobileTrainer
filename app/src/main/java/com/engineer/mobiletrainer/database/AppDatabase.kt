package com.engineer.mobiletrainer.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase


@Database(entities = [Settings::class], version = 1)
public abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val DB_NAME = "MobileTrainerDB"

        private var instance : AppDatabase? = null

        fun getInstance(context : Context) : AppDatabase {
            return instance ?: synchronized(this) {
                val instance2 = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).build()
                instance = instance2
                instance2
            }
        }
    }

    abstract fun settingsDao() : SettingsDao
}