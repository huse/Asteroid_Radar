package com.hus.asteroidradar.asteroidrepository

data class ResourcesData<out T>(val status: StatusEnumClass, val data: T?, val message: String?) {
    companion object {

        fun <T> loadInProgress(data: T?): ResourcesData<T> {
            return ResourcesData(StatusEnumClass.LOAD, data, null)
        }
        fun <T> successful(data: T?): ResourcesData<T> {
            return ResourcesData(StatusEnumClass.SUCCESSFULL, data, null)
        }

        fun <T> gotError(msg: String, data: T?): ResourcesData<T> {
            return ResourcesData(StatusEnumClass.ERROR, data, msg)
        }

    }
}

enum class StatusEnumClass {
    LOAD,
    ERROR,
    SUCCESSFULL

}