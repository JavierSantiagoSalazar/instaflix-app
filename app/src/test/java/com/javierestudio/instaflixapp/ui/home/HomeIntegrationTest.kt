package com.javierestudio.instaflixapp.ui.home

import app.cash.turbine.test
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.common.*
import com.javierestudio.instaflixapp.data.server.movies.RemoteMovie
import com.javierestudio.instaflixapp.data.server.tvshows.RemoteTvShow
import com.javierestudio.instaflixapp.testrules.CoroutinesTestRule
import com.javierestudio.instaflixapp.ui.home.HomeViewModel.UiState
import com.javierestudio.usecases.movie.GetMoviesByGenreUseCase
import com.javierestudio.usecases.movie.RequestPopularMoviesUseCase
import com.javierestudio.usecases.tvshow.GetTvShowByGenreUseCase
import com.javierestudio.usecases.tvshow.RequestPopularTvShowsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import com.javierestudio.instaflixapp.data.database.movie.Movie as DatabaseMovie
import com.javierestudio.instaflixapp.data.database.tvshow.TvShow as DatabaseTvShow

@ExperimentalCoroutinesApi
class HomeIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteMoviesData = buildRemoteMovies(4, 5, 6)
        val remoteTvShowsData = buildRemoteTvShows(4, 5, 6)
        val vm = buildViewModelWith(
            moviesRemoteData = remoteMoviesData,
            tvShowRemoteData = remoteTvShowsData
        )

        vm.getPrograms(false)

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = emptyList()), awaitItem())
            assertEquals(UiState(movies = emptyList(), tvShows = emptyList(), loading = false),
                awaitItem())
            assertEquals(UiState(movies = emptyList(), tvShows = emptyList(), loading = true),
                awaitItem())
            assertEquals(UiState(movies = emptyList(), tvShows = emptyList(), loading = false),
                awaitItem())

            val movies = awaitItem().movies!!
            assertEquals("Title 4", movies[0].title)
            assertEquals("Title 5", movies[1].title)
            assertEquals("Title 6", movies[2].title)

            val tvShows = awaitItem().tvShows!!

            assertEquals("Name 4", tvShows[0].name)
            assertEquals("Name 5", tvShows[1].name)
            assertEquals("Name 6", tvShows[2].name)
            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localMoviesData = buildDatabaseMovies(1, 2, 3, genre = ProgramGenre.POPULAR)
        val remoteMoviesData = buildRemoteMovies(4, 5, 6)
        val localTvShowsData = buildDatabaseTvShows(1, 2, 3, genre = ProgramGenre.POPULAR)
        val remoteTvShowsData = buildRemoteTvShows(4, 5, 6)

        val vm = buildViewModelWith(
            moviesRemoteData = remoteMoviesData,
            tvShowRemoteData = remoteTvShowsData,
            moviesLocalData = localMoviesData,
            tvShowLocalData = localTvShowsData
        )

        vm.state.test {
            assertEquals(UiState(), awaitItem())

            val movies = awaitItem().movies!!
            assertEquals("Title 1", movies[0].title)
            assertEquals("Title 2", movies[1].title)
            assertEquals("Title 3", movies[2].title)

            val tvShows = awaitItem().tvShows!!
            assertEquals("Name 1", tvShows[0].name)
            assertEquals("Name 2", tvShows[1].name)
            assertEquals("Name 3", tvShows[2].name)

            cancel()
        }
    }

    private fun buildViewModelWith(
        moviesLocalData: List<DatabaseMovie> = emptyList(),
        moviesRemoteData: List<RemoteMovie> = emptyList(),
        tvShowLocalData: List<DatabaseTvShow> = emptyList(),
        tvShowRemoteData: List<RemoteTvShow> = emptyList(),
    ): HomeViewModel {
        val moviesRepository = buildMovieRepositoryWith(moviesLocalData, moviesRemoteData)
        val tvShowRepository = buildTvShowRepositoryWith(tvShowLocalData, tvShowRemoteData)
        val getMoviesByGenreUseCase = GetMoviesByGenreUseCase(moviesRepository)
        val getTvShowByGenreUseCase = GetTvShowByGenreUseCase(tvShowRepository)
        val requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
        val requestPopularTvShowsUseCase = RequestPopularTvShowsUseCase(tvShowRepository)
        return HomeViewModel(
            getMoviesByGenreUseCase,
            getTvShowByGenreUseCase,
            requestPopularMoviesUseCase,
            requestPopularTvShowsUseCase
        )
    }
}
