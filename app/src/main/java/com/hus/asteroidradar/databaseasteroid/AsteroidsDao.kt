package com.hus.asteroidradar.databaseasteroid

import androidx.room.Dao
import androidx.room.Query
import com.hus.asteroidradar.Asteroid

@Dao
interface AsteroidsDao {
   @Query("SELECT * FROM Asteroid ORDER BY date(closeApproachDate) ASC")
    fun getAll(): List<Asteroid>

}