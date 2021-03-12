package com.hus.asteroidradar

import androidx.room.Entity
import com.squareup.moshi.Json

@Entity
data class PictureOfDay(@Json(name = "media_type") val mediaType: String, val title: String,
                        val url: String)