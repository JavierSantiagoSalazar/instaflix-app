package com.javierestudio.instaflixapp.data.database.movie

import com.javierestudio.data.datasource.movie.MovieLocalDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
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
        movieDao.getMoviesByGenre(ACTION_GENRE_ID).map { it.toDomainModel() }
    override val comedyMovies: Flow<List<Movie>> =
        movieDao.getMoviesByGenre(COMEDY_GENRE_ID).map { it.toDomainModel() }


    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0
    override suspend fun isMoviesByGenreEmpty(genre: Int): Boolean =
        movieDao.movieCountByGenreId(genre) == 0

    override fun findById(id: Int): Flow<Movie> = movieDao.findById(id).map { it.toDomainModel() }

    override suspend fun save(movies: List<Movie>): Error? = tryCall {
        movieDao.insertMovies(movies.fromDomainModel() )
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

private fun List<Movie>.fromDomainModel(): List<DbMovie> = map { it.fromDomainModel() }

private fun Movie.fromDomainModel(): DbMovie = DbMovie(
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
    favorite = favorite
)

const val ACTION_GENRE_ID = 28
const val COMEDY_GENRE_ID = 35
