package com.javierestudio.instaflixapp.data.database.movie

import com.javierestudio.data.common.Constants.ACTION_GENRE_ID
import com.javierestudio.data.common.Constants.COMEDY_GENRE_ID
import com.javierestudio.data.datasource.movie.MovieLocalDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.instaflixapp.data.database.ProgramType
import com.javierestudio.instaflixapp.data.database.convertToProgramType
import com.javierestudio.instaflixapp.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.javierestudio.instaflixapp.data.database.movie.Movie as DbMovie

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
) : MovieLocalDataSource {

    override val movies: Flow<List<Movie>> = movieDao.getAll().map { it.toDomainModel() }
    override val actionMovies: Flow<List<Movie>> =
        movieDao.getMoviesByGenreId(ACTION_GENRE_ID.convertToProgramType())
            .map { it.toDomainModel() }
    override val comedyMovies: Flow<List<Movie>> =
        movieDao.getMoviesByGenreId(COMEDY_GENRE_ID.convertToProgramType())
            .map { it.toDomainModel() }


    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0
    override suspend fun isMoviesEmptyByGenreId(genreId: Int): Boolean =
        movieDao.movieCountByProgramType(genreId.convertToProgramType()) == 0

    override fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>, genreId: Int): Error? = tryCall {
        movieDao.insertMovies(movies.fromDomainModel(genreId.convertToProgramType()))
    }.fold(
        ifLeft = { it },
        ifRight = { null }
    )
}

private fun List<DbMovie>.toDomainModel(): List<Movie> = map { it.toDomainModel() }

private fun DbMovie.toDomainModel(): Movie =
    Movie(
        id = id,
        title = title,
        overview = overview,
        releaseDate = releaseDate,
        posterPath = posterPath,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        genreIds = genreIds,
        popularity = popularity,
        voteAverage = voteAverage,
        favorite = favorite
    )

private fun List<Movie>.fromDomainModel(programType: ProgramType): List<DbMovie> =
    map { it.fromDomainModel(programType) }

private fun Movie.fromDomainModel(programType: ProgramType): DbMovie = DbMovie(
    id = id,
    title = title,
    overview = overview,
    releaseDate = releaseDate,
    posterPath = posterPath,
    backdropPath = backdropPath,
    originalLanguage = originalLanguage,
    genreIds = genreIds,
    originalTitle = originalTitle,
    popularity = popularity,
    voteAverage = voteAverage,
    favorite = favorite,
    programType = programType
)
