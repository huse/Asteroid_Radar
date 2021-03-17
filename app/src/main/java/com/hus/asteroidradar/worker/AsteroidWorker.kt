package com.hus.asteroidradar.worker

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.hus.asteroidradar.BuildConfig
import com.hus.asteroidradar.Constants
import com.hus.asteroidradar.api.NetworkUtils
import com.hus.asteroidradar.asteroidrepository.AsterServicesWeb
import com.hus.asteroidradar.databaseasteroid.AsteroidDatabase
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

class AsteroidWorker(appContext: Context, workerParams: WorkerParameters)
    : Worker(appContext, workerParams) {

    private val networkUtils: NetworkUtils = NetworkUtils()

    override fun doWork(): Result {

        val webServices = Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(ScalarsConverterFactory.create()).build().create(AsterServicesWeb::class.java)
        val callBack = webServices.getNEOFeed(apiKey = BuildConfig.NASA_ASTEROID_API_KEY)
        val responsesFromCallBack = callBack.PerformngWork()
        val asteroidDaoFromDataBase = AsteroidDatabase.getInstanceOfAsteroidDatabase(applicationContext).asteroidsDao()
        if (!responsesFromCallBack.isSuccessful || responsesFromCallBack.body().isNullOrEmpty()) {
            return Result.failure()
        }
        val asteroids = networkUtils.parseAsteroidsJsonResult(
            JSONObject(responsesFromCallBack.body() ?: "")
        )
        try {
            val rows = asteroidDaoFromDataBase.updateData(asteroids)
            if (rows.isEmpty()) {
                return Result.failure()
            }
        } catch (e: SQLiteConstraintException) {
            return Result.failure()
        }
        return Result.success()
    }

    fun <T> Call<T>.PerformngWork(): Response<T> {
        return try {
            this.execute()
        } catch (e: IOException) {
            Response.error<T>(
                400,
                ResponseBody.create(
                    MediaType.parse("text/plain"),
                    "Error: No Network"
                )
            )
        }
    }
}