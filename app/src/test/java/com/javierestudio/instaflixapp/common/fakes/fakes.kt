package com.javierestudio.instaflixapp.common.fakes

import com.javierestudio.data.PermissionChecker
import com.javierestudio.data.datasource.LocationDataSource
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.data.database.movie.MovieDao
import com.javierestudio.instaflixapp.data.database.tvshow.TvShowDao
import com.javierestudio.instaflixapp.data.server.movies.MovieRemoteService
import com.javierestudio.instaflixapp.data.server.movies.RemoteMovie
import com.javierestudio.instaflixapp.data.server.movies.MovieRemoteResult
import com.javierestudio.instaflixapp.data.server.tvshows.RemoteTvShow
import com.javierestudio.instaflixapp.data.server.tvshows.TvShowsRemoteResult
import com.javierestudio.instaflixapp.data.server.tvshows.TvShowRemoteService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import com.javierestudio.instaflixapp.data.database.movie.Movie as DatabaseMovie
import com.javierestudio.instaflixapp.data.database.tvshow.TvShow as DatabaseTvShow

class FakeMovieDao(movies: List<DatabaseMovie> = emptyList()) : MovieDao {

    private val inMemoryMovies = MutableStateFlow(movies)
    private lateinit var findMovieFlow: MutableStateFlow<DatabaseMovie>

    override fun findById(id: Int): Flow<DatabaseMovie> {
        findMovieFlow = MutableStateFlow(inMemoryMovies.value.first { it.id == id })
        return findMovieFlow
    }

    override fun getMoviesByGenreId(programGenre: ProgramGenre): Flow<List<DatabaseMovie>> {
        return inMemoryMovies.map { movies ->
            movies.filter { programGenre == it.programGenre }
        }
    }

    override suspend fun movieCount(): Int = inMemoryMovies.value.size
    override suspend fun movieCountByProgramGenre(programGenre: ProgramGenre): Int =
        inMemoryMovies.value.size


    override suspend fun insertMovies(movies: List<DatabaseMovie>) {
        inMemoryMovies.value = movies

        if (::findMovieFlow.isInitialized) {
            movies.firstOrNull() { it.id == findMovieFlow.value.id }
                ?.let { findMovieFlow.value = it }
        }

    }

    override suspend fun deleteMoviesByGenre(programGenre: ProgramGenre) {
        inMemoryMovies.value = emptyList()
    }

}

class FakeTvShowDao(tvShows: List<DatabaseTvShow> = emptyList()) : TvShowDao {

    private val inMemoryTvShows = MutableStateFlow(tvShows)
    private lateinit var findTvShowFlow: MutableStateFlow<DatabaseTvShow>

    override fun getTvShowsByGenreId(programGenre: ProgramGenre): Flow<List<DatabaseTvShow>> {
        return inMemoryTvShows.map { movies ->
            movies.filter { programGenre == it.programGenre }
        }
    }

    override fun findById(id: Int): Flow<DatabaseTvShow> {
        findTvShowFlow = MutableStateFlow(inMemoryTvShows.value.first { it.id == id })
        return findTvShowFlow
    }

    override suspend fun tvShowCount(): Int = inMemoryTvShows.value.size
    override suspend fun tvShowCountByProgramGenre(programGenre: ProgramGenre): Int =
        inMemoryTvShows.value.size

    override suspend fun insertTvShows(tvShows: List<DatabaseTvShow>) {
        inMemoryTvShows.value = tvShows

        if (::findTvShowFlow.isInitialized) {
            tvShows.firstOrNull() { it.id == findTvShowFlow.value.id }
                ?.let { findTvShowFlow.value = it }
        }
    }

    override suspend fun deleteTvShowsByGenre(programGenre: ProgramGenre) {
        inMemoryTvShows.value = emptyList()
    }
}

class FakeMovieRemoteService(private val movies: List<RemoteMovie> = emptyList()) :
    MovieRemoteService {

    override suspend fun listPopularMovies(apiKey: String, region: String) = MovieRemoteResult(
        page = 1,
        results = movies,
        totalPages = 1,
        totalResults = movies.size
    )

    override suspend fun listMoviesByGenre(apiKey: String, genre: Int): MovieRemoteResult =
        MovieRemoteResult(
            page = 1,
            results = movies,
            totalPages = 1,
            totalResults = movies.size
        )

}

class FakeTvShowRemoteService(private val tvShows: List<RemoteTvShow> = emptyList()) :
    TvShowRemoteService {

    override suspend fun listPopularTvShows(apiKey: String): TvShowsRemoteResult =
        TvShowsRemoteResult(
            page = 1,
            results = tvShows,
            totalPages = 1,
            totalResults = tvShows.size
        )

    override suspend fun listTvShowsByGenre(
        apiKey: String,
        genre: Int,
    ): TvShowsRemoteResult =
        TvShowsRemoteResult(
            page = 1,
            results = tvShows,
            totalPages = 1,
            totalResults = tvShows.size
        )

}

class FakeLocationDataSource : LocationDataSource {
    var location = "US"

    override suspend fun findLastRegion(): String = location
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override fun check(permission: PermissionChecker.Permission) = permissionGranted
}