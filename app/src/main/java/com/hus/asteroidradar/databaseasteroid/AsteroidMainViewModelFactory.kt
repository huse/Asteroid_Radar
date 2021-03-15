package com.hus.asteroidradar.databaseasteroid

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class AsteroidMainViewModelFactory(private val context: Context?): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = context?.let {
        AsteroidMainViewModel(
                it
        )
    } as T
}

/*class AsteroidMainViewModelFactory (

        private val dataSource: AsteroidsDao,
        private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return AsteroidMainViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}*/