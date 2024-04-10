package com.engineer.mobiletrainer.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope


@Database(entities = [Settings::class, Profile::class], version = 4)
public abstract class AppDatabase : RoomDatabase() {

    companion object {
        private val DB_NAME = "MobileTrainerDB"

        @Volatile
        private var instance : AppDatabase? = null

        fun getInstance(context : Context, scope: CoroutineScope) : AppDatabase {
            return instance ?: synchronized(this) {
                val instance2 = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME).fallbackToDestructiveMigration().build()
                instance = instance2
                instance2
            }
        }
    }

    abstract fun profileDao(): ProfileDao

    abstract fun settingsDao() : SettingsDao
}