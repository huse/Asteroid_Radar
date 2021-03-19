package com.hus.asteroidradar.databaseasteroid

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import java.util.*

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

/*    @Query("SELECT * FROM Asteroid WHERE date(closeApproachDate) >= :startDate AND date(closeApproachDate) <= :endDate ORDER BY date(closeApproachDate) ASC")
    fun getAsteroidsByDate(startDate: String, endDate: String): Flow<List<Asteroid>>*/

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate >= :fromDate AND :toDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsByDate2(fromDate: String, toDate: String): List<Asteroid>

    @Query("SELECT * FROM Asteroid WHERE closeApproachDate >= :fromDate AND :toDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsByDate4(fromDate: String, toDate: String): Flow<List<Asteroid>>

/*    @Query("SELECT * FROM Asteroid WHERE closeApproachDate BETWEEN :fromDate AND :toDate ORDER BY closeApproachDate ASC")
    fun getAsteroidsByDate3(fromDate: String, toDate: String): LiveData<List<AsteroidDatabase>>*/

}