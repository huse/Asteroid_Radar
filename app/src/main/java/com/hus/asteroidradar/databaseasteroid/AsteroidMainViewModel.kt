package com.hus.asteroidradar.databaseasteroid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class AsteroidMainViewModel (
    val database: AsteroidsDao,
    application: Application
) : AndroidViewModel(application) {


    private val _navigateToAsteroidDetails = MutableLiveData<Long>()

    fun onAsteroidClicked(id: Long) {
        _navigateToAsteroidDetails.value = id
    }
}