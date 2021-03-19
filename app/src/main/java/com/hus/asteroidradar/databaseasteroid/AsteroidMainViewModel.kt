package com.hus.asteroidradar.databaseasteroid

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
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import timber.log.Timber


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
    private val networkUtils: NetworkUtils = NetworkUtils()
    private val database = AsteroidDatabase.getInstanceOfAsteroidDatabase(applicationContext)

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

    val pictureOfDay: LiveData<ResourcesData<PictureOfDay>> = repository.gettingPictureOfTheDay()

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
    private val typeOfFilter = MutableLiveData(FilterType.TODAY)
    enum class FilterType(val type: String) {WEEK("Week"), TODAY("Today"), SAVE("Saved")}
    fun applyFilter(filterType: FilterType){

        Timber.i("mmmm  applyFilter selected:  " + filterType)
        typeOfFilter.value = filterType
    }
    fun onApplyFilter(filter: FilterType){

        Timber.i("mmmm  onApplyFilter selected:  " + filter)
        applyFilter(filter)
    }
    private var _asteroids = MutableLiveData<List<Asteroid>>()

    val asteroidsData: LiveData<List<Asteroid>>
        get() = _asteroids


    fun menuWeekClicked() {
        Timber.i("mmmm  menuWeekClicked :  ")
        viewModelScope.launch {
            database.asteroidsDao()
                .getAsteroidsByDate4(networkUtils.getTodaysData(), networkUtils.getWeekData())
        }

/*        val response = repository.gettingAsteroidDataWeb2(FilterType.TODAY.toString(), FilterType.WEEK.toString() )
        asteroidFeed.addSource(response) { newData ->
            if (asteroidFeed.value != newData) {
                asteroidFeed.value = newData
            }
        }*/
    }


    fun menuTodayClicked() {
        Timber.i("mmmm  menuTodayClicked :  ")
        viewModelScope.launch {
            database.asteroidsDao()
                .getAsteroidsByDate4(networkUtils.getTodaysData(), networkUtils.getTodaysData())

        }
    }

    fun menuSavedClicked() {
        Timber.i("mmmm  menuSavedClicked :  ")
        val response = repository.gettingAsteroidDataWeb()
        asteroidFeed.addSource(response) { newData ->
            if (asteroidFeed.value != newData) {
                asteroidFeed.value = newData
            }
        }
    }
    private fun gettinAllAstroidList() = database.gettinAllAstroidList()

/*    val asteroidsList =
        Transformations.map(typeOfFilter) { period ->
            period?.let {
                when (period) {
                    FilterType.TODAY   -> repository.today
                    FilterType.WEEK -> repository.week
                }
            }
        }*/
}

