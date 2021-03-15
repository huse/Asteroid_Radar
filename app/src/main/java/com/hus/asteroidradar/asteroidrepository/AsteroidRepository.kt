package com.hus.asteroidradar.asteroidrepository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hus.asteroidradar.BuildConfig
import com.hus.asteroidradar.api.NetworkUtils
import com.hus.asteroidradar.databaseasteroid.Asteroid
import com.hus.asteroidradar.databaseasteroid.AsteroidsDao
import com.hus.asteroidradar.databasepictureday.PictureOfDay
import com.hus.asteroidradar.databasepictureday.PictureOfDayDao
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineScope
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeUnit

class AsteroidRepository(
        private val asteroidsDao: AsteroidsDao,
        private val pictureOfDayDao: PictureOfDayDao,
        private val asterServicesWeb: AsterServicesWeb,
        private val viewModelScope: CoroutineScope,
        private val networkUtils: NetworkUtils) {
    fun gettingAsteroidDataWeb(): LiveData<ResourcesData<List<Asteroid>>> {
        return object : ResourcesNetwork<List<Asteroid>, String>(viewModelScope) {
            override suspend fun loadingFromDisks(): LiveData<List<Asteroid>> {
                return MutableLiveData(asteroidsDao.getAllData())
            }

            override fun shouldFetching(diskResponse: List<Asteroid>?): Boolean {
                return diskResponse.isNullOrEmpty()
            }

            override suspend fun fetchingData(): ResponsesFromWeb<String> {
                val call = asterServicesWeb.getNEOFeed(apiKey = BuildConfig.NASA_ASTEROID_API_KEY)
                val response = call.safeExecute()

                if (!response.isSuccessful || response.body().isNullOrEmpty()) {
                    return Failed(400, "Invalid Response")
                }

                return Successful(response.body() as String)
            }

            override fun processingResponse(response: String): List<Asteroid> {
                val json = JSONObject(response)

                return networkUtils.parseAsteroidsJsonResult(json)
            }

            override suspend fun savingToDisks(data: List<Asteroid>): Boolean {
                val ids = asteroidsDao.updateData(data)
                return ids.isNotEmpty()
            }
        }.dataLive()
    }

/*    fun gettingPictureOfTheDay(): LiveData<ResourcesData<PictureOfDay>> {
        return object : ResourcesNetwork<PictureOfDay, String>(viewModelScope) {
            override suspend fun loadFromDisk(): LiveData<PictureOfDay> {
                return MutableLiveData(pictureOfDayDao.get())
            }

            override fun shouldFetch(diskResponse: PictureOfDay?): Boolean {
                // Fetch if 24hr timestamp has expired
                return diskResponse == null
                        || diskResponse.timeMark +
                        TimeUnit.MILLISECONDS
                                .convert(24L, TimeUnit.HOURS) < System.currentTimeMillis()
            }

            override suspend fun fetchData(): ResponsesFromWeb<String> {
                val call = asterServicesWeb.getPictureOfDay(apiKey = BuildConfig.NASA_ASTEROID_API_KEY)
                val response = call.safeExecute()

                if (!response.isSuccessful || response.body().isNullOrEmpty()) {
                    return Failed(400, "Invalid Response")
                }

                return Successful(response.body() as String)
            }

            override fun processResponse(response: String): PictureOfDay {
                val pic = Moshi.Builder()
                        .build()
                        .adapter(PictureOfDay::class.java)
                        .fromJson(response)
                        ?:
                        // Return an empty picture
                        PictureOfDay(-1, "image", "", "")
                pic.timeMark = System.currentTimeMillis()
                return pic
            }

            override suspend fun saveToDisk(data: PictureOfDay): Boolean {
                return pictureOfDayDao.updateData(data) > 0
            }
        }.dataLive()
    }*/
    fun <T> Call<T>.safeExecute(): Response<T> {
        return try {
            this.execute()
        } catch (e: IOException) {
            Response.error<T>(400,
                    ResponseBody.create(MediaType.parse("text/plain"),
                            "Error: No Network"))
        }
    }
}

class Failed(val code: Int, val message: String) : ResponsesFromWeb<Nothing>()
class Successful<out T>(val data: T) : ResponsesFromWeb<T>()
sealed class ResponsesFromWeb<out T>