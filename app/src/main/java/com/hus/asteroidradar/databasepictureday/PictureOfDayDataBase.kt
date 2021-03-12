package com.hus.asteroidradar.databasepictureday

import androidx.room.Database
import com.hus.asteroidradar.PictureOfDay

@Database(entities = [PictureOfDay::class], version = 1)
abstract class PictureOfDayDataBase {
    abstract fun pictureDayDatabase(): PictureOfDayDao
}