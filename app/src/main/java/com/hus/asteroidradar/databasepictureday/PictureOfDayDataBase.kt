package com.hus.asteroidradar.databasepictureday

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hus.asteroidradar.PictureOfDay

@Database(entities = [PictureOfDay::class], version = 1)
abstract class PictureOfDayDataBase : RoomDatabase() {
    abstract fun pictureDayDatabase(): PictureOfDayDao
}