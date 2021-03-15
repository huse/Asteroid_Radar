package com.hus.asteroidradar.databaseasteroid

import androidx.room.*

@Dao
interface AsteroidsDao {

    @Transaction
    fun updateData(users: List<Asteroid>): List<Long> {
        deleteAllData()
        return insertAllData(users)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllData(asteroids: List<Asteroid>): List<Long>

    @Query("DELETE FROM Asteroid")
    fun deleteAllData()


   @Query("SELECT * FROM Asteroid ORDER BY date(closeApproachDate) ASC")
    fun getAllData(): List<Asteroid>

}