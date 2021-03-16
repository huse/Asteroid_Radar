package com.hus.asteroidradar.databaseasteroid

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.*
import androidx.room.Room
import com.hus.asteroidradar.Constants
import com.hus.asteroidradar.api.NetworkUtils
import com.hus.asteroidradar.asteroidrepository.AsterServicesWeb
import com.hus.asteroidradar.asteroidrepository.AsteroidRepository
import com.hus.asteroidradar.asteroidrepository.ResourcesData
import com.hus.asteroidradar.databasepictureday.PictureOfDay
import com.hus.asteroidradar.databasepictureday.PictureOfDayDataBase
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

/*
class AsteroidMainViewModel (
    val database: AsteroidsDao,
    application: Application
) : AndroidViewModel(application) {


    private val _navigateToAsteroidDetails = MutableLiveData<Long>()

    fun onAsteroidClicked(id: Long) {
        _navigateToAsteroidDetails.value = id
    }
}*/

class AsteroidMainViewModel(applicationContext: Context) : ViewModel() {

    private val repository: AsteroidRepository =
            AsteroidRepository(
                    AsteroidDatabase.getInstanceOfAsteroidDatabase(applicationContext)
                            .asteroidsDao(),
                    Room.databaseBuilder(
                            applicationContext,
                            PictureOfDayDataBase::class.java,
                            "pic-day-db"
                    )
                            .fallbackToDestructiveMigration()
                            .build()
                            .pictureDayDatabase(),
                    Retrofit.Builder().baseUrl(Constants.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build()
                            .create(AsterServicesWeb::class.java),
                    viewModelScope, networkUtils = NetworkUtils()
            )

    val asteroidFeed: MediatorLiveData<ResourcesData<List<Asteroid>>> = MediatorLiveData()
    val selectedAsteroid = MutableLiveData<Asteroid>()

 //   val pictureOfDay: LiveData<ResourcesData<PictureOfDay>> = repository.gettingPictureOfTheDay()

    fun selecting(item: Asteroid) {
        Log.i("hhhhh", "from AsteroidMainViewModel selecting called" + item)
        selectedAsteroid.value = item
    }

    /**
     * Fetch asteroid feeds and update live data.
     */
    fun gettingFed() {

        val response = repository.gettingAsteroidDataWeb()

        Log.i("hhhhh", "from AsteroidMainViewModel gettingFed called" + response)
        asteroidFeed.addSource(response) { newData ->
            if (asteroidFeed.value != newData) {
                asteroidFeed.value = newData
            }
        }
    }
}