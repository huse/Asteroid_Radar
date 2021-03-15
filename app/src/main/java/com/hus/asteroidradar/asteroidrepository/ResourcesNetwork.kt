package com.hus.asteroidradar.asteroidrepository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*

abstract class ResourcesNetwork<T, K>(private val viewModelScope: CoroutineScope) {

    /**
     * A [MediatorLiveData] resource.
     */
    private val results = MediatorLiveData<ResourcesData<T>>()

    init {
        launching()
    }

    fun dataLive(): LiveData<ResourcesData<T>> = results

    private fun launching() {
        viewModelScope.launch {
            val disksResources =  withContext(Dispatchers.IO) {loadFromDisk()}
            if (shouldFetch(disksResources.value)) {
                results.addSource(disksResources) { newData ->
                    setValue(ResourcesData.loadInProgress(newData))
                }
                results.removeSource(disksResources)
                val fetchingTask = async(Dispatchers.IO) { fetchData() }
                when (val response = fetchingTask.await()) {
                    is Successful -> {
                        val string = response.data.toString()
                        withContext(Dispatchers.IO) {
                            saveToDisk(processResponse(response.data))
                        }
                        val diskResponses = withContext(Dispatchers.IO) { loadFromDisk() }
                        results.addSource(diskResponses) { newData ->
                            setValue(ResourcesData.successful(newData))
                        }
                    }
                    is Failed -> {
                        results.addSource(disksResources) { newData ->
                            setValue(ResourcesData.gotError(response.message, newData))
                        }
                    }
                }
            } else {
                results.addSource(disksResources) { data ->
                    setValue(ResourcesData.successful(data))
                }
            }
        }
    }
    @MainThread
    private fun setValue(newValue: ResourcesData<T>) {
        if (results.value != newValue) {
            results.value = newValue
        }
    }


    @WorkerThread
    abstract suspend fun loadFromDisk(): LiveData<T>
    @MainThread
    abstract fun shouldFetch(diskResponse: T?): Boolean
    @WorkerThread
    abstract suspend fun fetchData(): ResponsesFromWeb<K>
    @MainThread
    abstract fun processResponse(response: K): T
    @WorkerThread
    abstract suspend fun saveToDisk(data: T): Boolean
}