package com.javierestudio.instaflixapp.data.database.movie

import com.javierestudio.data.datasource.movie.MovieLocalDataSource
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.domain.ProgramType
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.data.database.convertToProgramGenre
import com.javierestudio.instaflixapp.data.tryCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.javierestudio.instaflixapp.data.database.movie.Movie as DbMovie

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
) : MovieLocalDataSource {

    override val movies: Flow<List<Movie>> =
        movieDao.getMoviesByGenreId(ProgramGenre.POPULAR)
            .map { it.toDomainModel(ProgramGenre.POPULAR) }

    override val actionMovies: Flow<List<Movie>> =
        movieDao.getMoviesByGenreId(ProgramGenre.ACTION)
            .map { it.toDomainModel(ProgramGenre.ACTION) }

    override val comedyMovies: Flow<List<Movie>> =
        movieDao.getMoviesByGenreId(ProgramGenre.COMEDY)
            .map { it.toDomainModel(ProgramGenre.COMEDY) }


    override suspend fun isEmpty(): Boolean = movieDao.movieCount() == 0
    override suspend fun isMoviesEmptyByGenreId(genreId: Int): Boolean =
        movieDao.movieCountByProgramGenre(genreId.convertToProgramGenre()) == 0

    override fun findById(id: Int): Flow<Movie> =
        movieDao.findById(id).map { it.toDomainModel(it.programGenre) }

    override suspend fun save(movies: List<Movie>, genreId: Int): Error? =
        tryCall {
            movieDao.insertMovies(movies.fromDomainModel(genreId.convertToProgramGenre()))
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

    override suspend fun deleteMoviesByGenre(genre: ProgramGenre): Error? =
        tryCall {
            movieDao.deleteMoviesByGenre(genre)
        }.fold(
            ifLeft = { it },
            ifRight = { null }
        )

}

private fun List<DbMovie>.toDomainModel(programGenre: ProgramGenre): List<Movie> =
    map { it.toDomainModel(programGenre) }

private fun DbMovie.toDomainModel(programGenre: ProgramGenre): Movie =
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
        programType = ProgramType.MOVIE,
        programGenre = programGenre
    )

private fun List<Movie>.fromDomainModel(programGenre: ProgramGenre): List<DbMovie> =
    map { it.fromDomainModel(programGenre) }

private fun Movie.fromDomainModel(programGenre: ProgramGenre): DbMovie =
    DbMovie(
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
        programGenre = programGenre,
    )
