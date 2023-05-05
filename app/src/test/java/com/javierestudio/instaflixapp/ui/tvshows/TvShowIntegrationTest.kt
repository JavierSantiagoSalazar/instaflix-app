package com.javierestudio.instaflixapp.ui.tvshows

import app.cash.turbine.test
import com.javierestudio.apptestshared.FakeNetworkHelper
import com.javierestudio.apptestshared.buildDatabaseTvShows
import com.javierestudio.apptestshared.buildRemoteTvShows
import com.javierestudio.apptestshared.buildTvShowRepositoryWith
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.data.database.tvshow.TvShow
import com.javierestudio.instaflixapp.data.server.tvshows.RemoteTvShow
import com.javierestudio.instaflixapp.testrules.CoroutinesTestRule
import com.javierestudio.instaflixapp.ui.tvshows.TvShowsViewModel.UiState
import com.javierestudio.usecases.tvshow.GetTvShowByGenreUseCase
import com.javierestudio.usecases.tvshow.RequestTvShowsByGenreIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TvShowIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteAnimationData = buildRemoteTvShows(4, 5, 6)
        val vm = buildViewModelWith(tvShowRemoteData = remoteAnimationData)

        vm.getPrograms(false)

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(animationTvShows = emptyList()), awaitItem())
            Assert.assertEquals(UiState(animationTvShows = emptyList(),
                dramaTvShows = emptyList(),
                loading = false),
                awaitItem())
            Assert.assertEquals(UiState(animationTvShows = emptyList(),
                dramaTvShows = emptyList(),
                loading = true),
                awaitItem())
            Assert.assertEquals(UiState(animationTvShows = emptyList(),
                dramaTvShows = emptyList(),
                loading = false),
                awaitItem())

            val animationTvShows = awaitItem().animationTvShows!!
            Assert.assertEquals("Name 4", animationTvShows[0].name)
            Assert.assertEquals("Name 5", animationTvShows[1].name)
            Assert.assertEquals("Name 6", animationTvShows[2].name)
            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localTvShowData = buildDatabaseTvShows(1, 2, 3, genre = ProgramGenre.ANIMATION)
        val remoteTvShowData = buildRemoteTvShows(4, 5, 6)

        val vm = buildViewModelWith(
            tvShowLocalData = localTvShowData,
            tvShowRemoteData = remoteTvShowData,
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())

            val movies = awaitItem().animationTvShows!!
            Assert.assertEquals("Name 1", movies[0].name)
            Assert.assertEquals("Name 2", movies[1].name)
            Assert.assertEquals("Name 3", movies[2].name)
            Assert.assertEquals(awaitItem().dramaTvShows!!.size, 0)
            cancel()
        }
    }

    private fun buildViewModelWith(
        tvShowLocalData: List<TvShow> = emptyList(),
        tvShowRemoteData: List<RemoteTvShow> = emptyList(),
    ): TvShowsViewModel {
        val tvShowRepository = buildTvShowRepositoryWith(tvShowLocalData, tvShowRemoteData)
        val getMoviesByGenreUseCase = GetTvShowByGenreUseCase(tvShowRepository)
        val requestPopularMoviesUseCase = RequestTvShowsByGenreIdUseCase(tvShowRepository)
        val networkHelper = FakeNetworkHelper(true)
        return TvShowsViewModel(getMoviesByGenreUseCase, requestPopularMoviesUseCase, networkHelper)
    }
}
