package com.javierestudio.domain

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val genreIds:  List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val popularity: Double,
    val voteAverage: Double,
    val programType: ProgramType,
    val programGenre: ProgramGenre
)
