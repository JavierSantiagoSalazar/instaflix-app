package com.javierestudio.instaflixapp.ui.detail

import app.cash.turbine.test
import com.javierestudio.domain.ProgramType
import com.javierestudio.instaflixapp.testrules.CoroutinesTestRule
import com.javierestudio.instaflixapp.ui.detail.DetailViewModel.UiState
import com.javierestudio.testshared.sampleMovie
import com.javierestudio.testshared.sampleTvShow
import com.javierestudio.usecases.detail.FindMovieUseCase
import com.javierestudio.usecases.detail.FindTvShowUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var findMovieUseCase: FindMovieUseCase

    @Mock
    lateinit var findTvShowUseCase: FindTvShowUseCase

    private lateinit var vm: DetailViewModel

    private val movie = sampleMovie.copy(id = 2)
    private val tvShow = sampleTvShow.copy(id = 2)

    @Test
    fun `UI is updated with the movie on start`() = runTest {
        whenever(findMovieUseCase(2)).thenReturn(flowOf(movie))
        vm = DetailViewModel(2, ProgramType.MOVIE , findMovieUseCase, findTvShowUseCase)

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(movie = movie), awaitItem())
            cancel()
        }
    }

    @Test
    fun `UI is updated with the tvShow on start`() = runTest {
        whenever(findTvShowUseCase(2)).thenReturn(flowOf(tvShow))
        vm = DetailViewModel(2, ProgramType.TV_SHOW , findMovieUseCase, findTvShowUseCase)

        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(tvShow = tvShow), awaitItem())
            cancel()
        }
    }
}
