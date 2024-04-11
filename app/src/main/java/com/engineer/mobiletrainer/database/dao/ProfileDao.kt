package com.engineer.mobiletrainer.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.engineer.mobiletrainer.database.entity.Profile
import com.engineer.mobiletrainer.database.entity.relations.ProfileWithPlans
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile ORDER BY name ASC")
    fun getAll(): Flow<List<Profile>>

    @Query("SELECT * FROM profile WHERE name = :named")
    fun getProfileOf(named: String): Profile
    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateProfile(profile: Profile)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profile: Profile)

    @Query("DELETE FROM profile WHERE name = :name AND surname = :surname")
    suspend fun deleteProfileOf(name: String, surname: String)



}