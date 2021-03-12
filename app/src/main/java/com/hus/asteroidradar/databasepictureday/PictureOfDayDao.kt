package com.hus.asteroidradar.databasepictureday

import androidx.room.Dao
import androidx.room.Query
import com.hus.asteroidradar.PictureOfDay


@Dao
interface PictureOfDayDao {
    @Query("SELECT * FROM pictureofday")
    fun get(): PictureOfDay
}