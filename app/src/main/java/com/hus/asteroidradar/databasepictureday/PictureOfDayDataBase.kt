package com.hus.asteroidradar.databasepictureday

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PictureOfDay::class], version = 2)
abstract class PictureOfDayDataBase : RoomDatabase() {
    abstract fun pictureDayDatabase(): PictureOfDayDao
}