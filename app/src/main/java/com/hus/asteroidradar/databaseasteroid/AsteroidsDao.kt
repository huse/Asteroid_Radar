package com.hus.asteroidradar.databaseasteroid

import androidx.room.Dao
import androidx.room.Query

@Dao
interface AsteroidsDao {
   @Query("SELECT * FROM Asteroid ORDER BY date(closeApproachDate) ASC")
    fun getAll(): List<Asteroid>

}