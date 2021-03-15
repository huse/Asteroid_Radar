package com.hus.asteroidradar.asteroidrepository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.*

abstract class ResourcesNetwork<T, K>(private val viewModelScope: CoroutineScope) {

    private val results = MediatorLiveData<ResourcesData<T>>()

    init {
        launching()
    }

    fun dataLive(): LiveData<ResourcesData<T>> = results

    private fun launching() {
        viewModelScope.launch {
            val disksResources =  withContext(Dispatchers.IO) {loadingFromDisks()}
            if (shouldFetching(disksResources.value)) {
                results.addSource(disksResources) { newData ->
                    setValues(ResourcesData.loadInProgress(newData))
                }
                results.removeSource(disksResources)
                val fetchingTask = async(Dispatchers.IO) { fetchingData() }
                when (val response = fetchingTask.await()) {
                    is Successful -> {
                        val string = response.data.toString()
                        withContext(Dispatchers.IO) {
                            savingToDisks(processingResponse(response.data))
                        }
                        val diskResponses = withContext(Dispatchers.IO) { loadingFromDisks() }
                        results.addSource(diskResponses) { newData ->
                            setValues(ResourcesData.successful(newData))
                        }
                    }
                    is Failed -> {
                        results.addSource(disksResources) { newData ->
                            setValues(ResourcesData.gotError(response.message, newData))
                        }
                    }
                }
            } else {
                results.addSource(disksResources) { data ->
                    setValues(ResourcesData.successful(data))
                }
            }
        }
    }
    @MainThread
    private fun setValues(newValues: ResourcesData<T>) {
        if (results.value != newValues) {results.value = newValues}
    }
    @WorkerThread
    abstract suspend fun loadingFromDisks(): LiveData<T>
    @MainThread
    abstract fun shouldFetching(diskRespond: T?): Boolean
    @WorkerThread
    abstract suspend fun fetchingData(): ResponsesFromWeb<K>
    @MainThread
    abstract fun processingResponse(responses: K): T
    @WorkerThread
    abstract suspend fun savingToDisks(data: T): Boolean
}