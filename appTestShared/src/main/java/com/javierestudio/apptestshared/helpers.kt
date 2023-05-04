package com.javierestudio.apptestshared

import com.javierestudio.data.RegionRepository
import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.data.repository.tvshow.TvShowRepository
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.data.database.movie.MovieLocalDataSourceImpl
import com.javierestudio.instaflixapp.data.database.tvshow.TvShowLocalDataSourceImpl
import com.javierestudio.instaflixapp.data.server.movies.MovieRemoteDataSourceImpl
import com.javierestudio.instaflixapp.data.server.movies.RemoteMovie
import com.javierestudio.instaflixapp.data.server.tvshows.RemoteTvShow
import com.javierestudio.instaflixapp.data.server.tvshows.TvShowRemoteDataSourceImpl
import com.javierestudio.instaflixapp.data.database.movie.Movie as DatabaseMovie
import com.javierestudio.instaflixapp.data.database.tvshow.TvShow as DatabaseTvShow

fun buildMovieRepositoryWith(
    localData: List<DatabaseMovie>,
    remoteData: List<RemoteMovie>,
): MoviesRepository {
    val locationDataSource = FakeLocationDataSource()
    val permissionChecker = FakePermissionChecker()
    val regionRepository = RegionRepository(locationDataSource, permissionChecker)
    val localDataSource = MovieLocalDataSourceImpl(FakeMovieDao(localData))
    val remoteDataSource = MovieRemoteDataSourceImpl("1234", FakeMovieRemoteService(remoteData))
    return MoviesRepository(regionRepository, localDataSource, remoteDataSource)
}

fun buildTvShowRepositoryWith(
    localData: List<DatabaseTvShow>,
    remoteData: List<RemoteTvShow>,
): TvShowRepository {
    val localDataSource = TvShowLocalDataSourceImpl(FakeTvShowDao(localData))
    val remoteDataSource = TvShowRemoteDataSourceImpl("1234", FakeTvShowRemoteService(remoteData))
    return TvShowRepository(localDataSource, remoteDataSource)
}

fun buildDatabaseMovies(vararg id: Int, genre: ProgramGenre) = id.map {
    DatabaseMovie(
        id = it,
        title = "Title $it",
        overview = "Overview $it",
        releaseDate = "01/01/2025",
        posterPath = "",
        backdropPath = "",
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        popularity = 5.0,
        voteAverage = 5.1,
        programGenre = genre,
        genreIds = listOf()
    )
}

fun buildDatabaseTvShows(vararg id: Int, genre: ProgramGenre) = id.map {
    DatabaseTvShow(
        id = it,
        name = "Name $it",
        overview = "Overview $it",
        firstAirDate = "01/01/2025",
        posterPath = "",
        backdropPath = "",
        originalLanguage = "EN",
        originalName = "Original Name $it",
        popularity = 5.0,
        voteAverage = 5.1,
        programGenre = genre,
    )
}

fun buildRemoteMovies(vararg id: Int) = id.map {
    RemoteMovie(
        adult = false,
        backdropPath = "",
        genreIds = listOf(28, 35),
        id = it,
        originalLanguage = "EN",
        originalTitle = "Original Title $it",
        overview = "Overview $it",
        popularity = 5.0,
        posterPath = "",
        releaseDate = "01/01/2025",
        title = "Title $it",
        video = false,
        voteAverage = 5.1,
        voteCount = 10
    )
}

fun buildRemoteTvShows(vararg id: Int) = id.map {
    RemoteTvShow(
        backdropPath = "",
        genreIds = emptyList(),
        id = it,
        originalLanguage = "EN",
        originalName = "Original Name $it",
        overview = "Overview $it",
        popularity = 5.0,
        posterPath = "",
        firstAirDate = "01/01/2025",
        name = "Name $it",
        voteAverage = 5.1,
        voteCount = 10
    )
}
