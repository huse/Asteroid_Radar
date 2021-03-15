package com.hus.asteroidradar.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hus.asteroidradar.databaseasteroid.Asteroid
enum class AsteroidApiStatus { LOADING, ERROR, DONE }
class MainViewModel(context: Context) : ViewModel() {
    private val _navigateToasteroidDetails = MutableLiveData<Asteroid>()
    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToasteroidDetails.value = asteroid
    }
    val navigateToAsteroidDetails: LiveData<Asteroid>
        get() = _navigateToasteroidDetails

    fun displayAsteroidDetailsComplete() {
        _navigateToasteroidDetails.value = null
    }
}