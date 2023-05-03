package com.javierestudio.domain

data class TvShow(
    val id: Int,
    val name: String,
    val overview: String,
    val firstAirDate: String,
    val posterPath: String,
    val backdropPath: String,
    val originalLanguage: String,
    val originalName: String,
    val popularity: Double,
    val voteAverage: Double,
    val programType: ProgramType,
    val programGenre: ProgramGenre
)
