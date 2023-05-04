package com.javierestudio.testshared

import com.javierestudio.domain.Movie
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.domain.ProgramType
import com.javierestudio.domain.TvShow


val sampleMovie = Movie(
    id = 0,
    title = "Title",
    overview = "Overview",
    releaseDate = "01/01/2025",
    posterPath = "",
    backdropPath = "",
    genreIds = listOf(28),
    originalLanguage = "EN",
    originalTitle = "Title",
    popularity = 5.0,
    voteAverage = 5.1,
    programGenre = ProgramGenre.ACTION,
    programType = ProgramType.MOVIE
)

val sampleTvShow = TvShow(
    id = 0,
    name = "Title",
    overview = "Overview",
    firstAirDate = "01/01/2025",
    posterPath = "",
    backdropPath = "",
    originalLanguage = "EN",
    originalName = "Title",
    popularity = 5.0,
    voteAverage = 5.1,
    programGenre = ProgramGenre.ACTION,
    programType = ProgramType.MOVIE
)
