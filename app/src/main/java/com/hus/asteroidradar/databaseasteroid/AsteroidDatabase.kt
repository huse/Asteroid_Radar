package com.hus.asteroidradar.databaseasteroid

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hus.asteroidradar.Asteroid

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase(){

    companion object {
        @Volatile
        private var instanceOfAstDt: AsteroidDatabase? = null

        fun getInstance(context: Context): AsteroidDatabase {
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

}