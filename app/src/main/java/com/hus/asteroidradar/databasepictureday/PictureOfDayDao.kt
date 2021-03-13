package com.hus.asteroidradar.databasepictureday

import androidx.room.*


@Dao
interface PictureOfDayDao {

    @Query("DELETE FROM PictureOfDay")
    fun deleteAll()

    @Transaction
    fun updateData(picture: PictureOfDay): Long {
        deleteAll()
        return insert(picture)
    }

   @Query("SELECT * FROM PictureOfDay")
    fun get(): PictureOfDay

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(picture: PictureOfDay): Long


}