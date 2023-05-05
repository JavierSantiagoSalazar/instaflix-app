package com.javierestudio.instaflixapp.ui.home

import app.cash.turbine.test
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.testrules.CoroutinesTestRule
import com.javierestudio.instaflixapp.ui.common.networkhelper.NetworkHelper
import com.javierestudio.instaflixapp.ui.home.HomeViewModel.UiState
import com.javierestudio.testshared.sampleMovie
import com.javierestudio.testshared.sampleTvShow
import com.javierestudio.usecases.movie.GetMoviesByGenreUseCase
import com.javierestudio.usecases.movie.RequestPopularMoviesUseCase
import com.javierestudio.usecases.tvshow.GetTvShowByGenreUseCase
import com.javierestudio.usecases.tvshow.RequestPopularTvShowsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getMoviesByGenreUseCase: GetMoviesByGenreUseCase

    @Mock
    lateinit var getTvShowByGenreUseCase: GetTvShowByGenreUseCase

    @Mock
    lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    @Mock
    lateinit var requestPopularTvShowsUseCase: RequestPopularTvShowsUseCase

    @Mock
    lateinit var networkHelper: NetworkHelper

    private lateinit var vm: HomeViewModel

    private val movies = listOf(sampleMovie.copy(id = 1, programGenre = ProgramGenre.POPULAR))
    private val tvShows = listOf(sampleTvShow.copy(id = 1, programGenre = ProgramGenre.POPULAR))

    @Before
    fun setup() {
        whenever(getMoviesByGenreUseCase(ProgramGenre.POPULAR)).thenReturn(flowOf(movies))
        whenever(getTvShowByGenreUseCase(ProgramGenre.POPULAR)).thenReturn(flowOf(tvShows))
        vm = HomeViewModel(
            getMoviesByGenreUseCase,
            getTvShowByGenreUseCase,
            networkHelper,
            requestPopularMoviesUseCase,
            requestPopularTvShowsUseCase
        )
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movies = movies), awaitItem())
            assertEquals(UiState(movies = movies, tvShows = tvShows), awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is shown when screen starts and hidden when it finishes requesting movies`() =
        runTest {
            vm.getPrograms()

            vm.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(movies = movies), awaitItem())
                assertEquals(UiState(movies = movies, tvShows = tvShows), awaitItem())
                assertEquals(UiState(movies = movies, tvShows = tvShows, loading = true),
                    awaitItem())
                assertEquals(UiState(movies = movies, tvShows = tvShows, loading = false),
                    awaitItem())
                cancel()
            }
        }

    @Test
    fun `Popular movies and tvShows are requested when UI screen starts`() = runTest {
        vm.getPrograms()
        runCurrent()

        verify(requestPopularMoviesUseCase).invoke(false)
        verify(requestPopularTvShowsUseCase).invoke(false)
    }

}
