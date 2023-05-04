package com.javierestudio.instaflixapp.ui.movies

import app.cash.turbine.test
import com.javierestudio.data.common.Constants
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.testrules.CoroutinesTestRule
import com.javierestudio.instaflixapp.ui.home.HomeViewModel
import com.javierestudio.instaflixapp.ui.movies.MoviesViewModel.UiState
import com.javierestudio.testshared.sampleMovie
import com.javierestudio.testshared.sampleTvShow
import com.javierestudio.usecases.movie.GetMoviesByGenreUseCase
import com.javierestudio.usecases.movie.RequestMoviesByGenreIdUseCase
import com.javierestudio.usecases.movie.RequestPopularMoviesUseCase
import com.javierestudio.usecases.tvshow.GetTvShowByGenreUseCase
import com.javierestudio.usecases.tvshow.RequestPopularTvShowsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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
class MoviesViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getMoviesByGenreUseCase: GetMoviesByGenreUseCase

    @Mock
    lateinit var requestMoviesByGenreIdUseCase: RequestMoviesByGenreIdUseCase

    private lateinit var vm: MoviesViewModel

    private val actionMovies = listOf(sampleMovie.copy(id = 1, programGenre = ProgramGenre.ACTION))
    private val comedyMovies = listOf(sampleMovie.copy(id = 1, programGenre = ProgramGenre.COMEDY))

    @Before
    fun setup() {
        whenever(getMoviesByGenreUseCase(ProgramGenre.ACTION)).thenReturn(flowOf(actionMovies))
        whenever(getMoviesByGenreUseCase(ProgramGenre.COMEDY)).thenReturn(flowOf(comedyMovies))
        vm = MoviesViewModel(
            getMoviesByGenreUseCase,
            requestMoviesByGenreIdUseCase
        )
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(actionMovies = actionMovies), awaitItem())
            assertEquals(UiState(actionMovies = actionMovies, comedyMovies = comedyMovies),
                awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is shown when screen starts and hidden when it finishes requesting movies`() =
        runTest {
            vm.getPrograms()

            vm.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(actionMovies = actionMovies), awaitItem())
                assertEquals(
                    UiState(
                        actionMovies = actionMovies,
                        comedyMovies = comedyMovies
                    ),
                    awaitItem()
                )
                assertEquals(
                    UiState(
                        actionMovies = actionMovies,
                        comedyMovies = comedyMovies,
                        loading = true
                    ),
                    awaitItem()
                )
                assertEquals(
                    UiState(
                        actionMovies = actionMovies,
                        comedyMovies = comedyMovies,
                        loading = false
                    ),
                    awaitItem())
                cancel()
            }
        }

    @Test
    fun `Action and Comedy movies are requested when UI screen starts`() = runTest {
        vm.getPrograms()
        runCurrent()

        verify(requestMoviesByGenreIdUseCase).invoke(false,
            Constants.ACTION_GENRE_ID,
            ProgramGenre.ACTION)
        verify(requestMoviesByGenreIdUseCase).invoke(false,
            Constants.COMEDY_GENRE_ID,
            ProgramGenre.COMEDY)
    }

}
