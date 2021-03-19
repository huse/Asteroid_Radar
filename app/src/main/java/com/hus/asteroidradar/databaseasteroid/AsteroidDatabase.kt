package com.hus.asteroidradar.databaseasteroid

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Asteroid::class], version = 2, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase(){

    companion object {
        @Volatile
        private var instanceOfAstDt: AsteroidDatabase? = null

        fun getInstanceOfAsteroidDatabase(context: Context): AsteroidDatabase {
            if (instanceOfAstDt == null) {
                instanceOfAstDt = Room
                    .databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroid-db"
                    )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instanceOfAstDt!!
        }
    }

    abstract fun asteroidsDao(): AsteroidsDao

     fun gettinAllAstroidList() = asteroidsDao().getAllData()
}