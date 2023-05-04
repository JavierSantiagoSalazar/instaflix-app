package com.javierestudio.instaflixapp.ui.detail

import app.cash.turbine.test
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.domain.ProgramType
import com.javierestudio.instaflixapp.common.buildDatabaseMovies
import com.javierestudio.instaflixapp.common.buildDatabaseTvShows
import com.javierestudio.instaflixapp.common.buildMovieRepositoryWith
import com.javierestudio.instaflixapp.common.buildTvShowRepositoryWith
import com.javierestudio.instaflixapp.data.database.tvshow.TvShow as DatabaseTvShow
import com.javierestudio.instaflixapp.data.database.movie.Movie as DatabaseMovie
import com.javierestudio.instaflixapp.data.server.movies.RemoteMovie
import com.javierestudio.instaflixapp.data.server.tvshows.RemoteTvShow
import com.javierestudio.instaflixapp.testrules.CoroutinesTestRule
import com.javierestudio.instaflixapp.ui.detail.DetailViewModel.UiState
import com.javierestudio.usecases.detail.FindMovieUseCase
import com.javierestudio.usecases.detail.FindTvShowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DetailIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        val vm = buildViewModelWith(
            id = 2,
            movieLocalData = buildDatabaseMovies(1, 2, 3, genre = ProgramGenre.ACTION),
            programType = ProgramType.MOVIE
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(2, awaitItem().movie!!.id)
            cancel()
        }
    }

    @Test
    fun `UI is updated with the tvShow on start`() = runTest {
        val vm = buildViewModelWith(
            id = 6,
            tvShowLocalData = buildDatabaseTvShows(4, 5, 6, genre = ProgramGenre.ANIMATION),
            programType = ProgramType.TV_SHOW
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(6, awaitItem().tvShow!!.id)
            cancel()
        }
    }

    private fun buildViewModelWith(
        id: Int,
        movieLocalData: List<DatabaseMovie> = emptyList(),
        movieRemoteData: List<RemoteMovie> = emptyList(),
        tvShowLocalData: List<DatabaseTvShow> = emptyList(),
        tvShowRemoteData: List<RemoteTvShow> = emptyList(),
        programType: ProgramType
    ): DetailViewModel {
        val moviesRepository = buildMovieRepositoryWith(movieLocalData, movieRemoteData)
        val tvShowRepository = buildTvShowRepositoryWith(tvShowLocalData, tvShowRemoteData)
        val findMovieUseCase = FindMovieUseCase(moviesRepository)
        val findTvShowUseCase = FindTvShowUseCase(tvShowRepository)
        return DetailViewModel(id, programType, findMovieUseCase, findTvShowUseCase)
    }
}
