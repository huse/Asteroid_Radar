package com.hus.asteroidradar.asteroidrepository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

interface AsterServicesWeb {
    @GET("planetary/apod")
    fun getPictureOfDay(@Query("api_key") apiKey: String): Call<String>
    @GET("neo/rest/v1/feed")
    fun getNEOFeed(@Query("detailed") detailed: Boolean = false,
                   @Query("api_key") apiKey: String): Call<String>
}