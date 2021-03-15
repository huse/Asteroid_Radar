package com.hus.asteroidradar.databasepictureday

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class PictureOfDay(@PrimaryKey(autoGenerate = true) val uid: Long,
                        @Json(name = "media_type") val mediaType: String?,
                        val title: String?,
                        val url: String?){
    var timeMark: Long = 0
}