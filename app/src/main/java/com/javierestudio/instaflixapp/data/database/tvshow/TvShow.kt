package com.javierestudio.instaflixapp.data.database.tvshow

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TvShow(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val overview: String,
    val firstAirDate: String,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val originalName: String,
    val popularity: Double,
    val voteAverage: Double,
    val favorite: Boolean,
)