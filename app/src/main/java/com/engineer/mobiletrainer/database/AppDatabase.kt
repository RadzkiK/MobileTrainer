package com.engineer.mobiletrainer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.engineer.mobiletrainer.database.dao.ExerciseDao
import com.engineer.mobiletrainer.database.dao.ExerciseSessionDao
import com.engineer.mobiletrainer.database.dao.ExerciseSetDao
import com.engineer.mobiletrainer.database.dao.PlansDao
import com.engineer.mobiletrainer.database.dao.PlansExerciseCrossRefDao
import com.engineer.mobiletrainer.database.dao.ProfileDao
import com.engineer.mobiletrainer.database.dao.SettingsDao
import com.engineer.mobiletrainer.database.dao.TrainingSessionDao
import com.engineer.mobiletrainer.database.entity.Exercise
import com.engineer.mobiletrainer.database.entity.ExerciseSession
import com.engineer.mobiletrainer.database.entity.ExerciseSet
import com.engineer.mobiletrainer.database.entity.Plans
import com.engineer.mobiletrainer.database.entity.PlansExerciseCrossRef
import com.engineer.mobiletrainer.database.entity.Profile
import com.engineer.mobiletrainer.database.entity.Settings
import com.engineer.mobiletrainer.database.entity.TrainingSession
import kotlinx.coroutines.CoroutineScope


@Database(entities = [Settings::class, Profile::class, Plans::class, Exercise::class, ExerciseSet::class, ExerciseSession::class, TrainingSession::class, PlansExerciseCrossRef::class], version = 9)
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
    abstract fun plansDao(): PlansDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun trainingSessionDao(): TrainingSessionDao
    abstract fun exerciseSessionDao(): ExerciseSessionDao
    abstract fun exercisesSetDao(): ExerciseSetDao
    abstract fun plansExerciseCrossRefDao(): PlansExerciseCrossRefDao
}