package com.javierestudio.instaflixapp.ui.tvshows

import app.cash.turbine.test
import com.javierestudio.data.common.Constants
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.instaflixapp.testrules.CoroutinesTestRule
import com.javierestudio.instaflixapp.ui.tvshows.TvShowsViewModel.UiState
import com.javierestudio.testshared.sampleTvShow
import com.javierestudio.usecases.tvshow.GetTvShowByGenreUseCase
import com.javierestudio.usecases.tvshow.RequestTvShowsByGenreIdUseCase
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
class TvShowsViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var getTvShowByGenreUseCase: GetTvShowByGenreUseCase

    @Mock
    lateinit var requestTvShowsByGenreIdUseCase: RequestTvShowsByGenreIdUseCase

    private lateinit var vm: TvShowsViewModel

    private val animationTvShows =
        listOf(sampleTvShow.copy(id = 1, programGenre = ProgramGenre.ANIMATION))
    private val dramaTvShows = listOf(sampleTvShow.copy(id = 1, programGenre = ProgramGenre.DRAMA))

    @Before
    fun setup() {
        whenever(getTvShowByGenreUseCase(ProgramGenre.ANIMATION)).thenReturn(flowOf(animationTvShows))
        whenever(getTvShowByGenreUseCase(ProgramGenre.DRAMA)).thenReturn(flowOf(dramaTvShows))
        vm = TvShowsViewModel(
            getTvShowByGenreUseCase,
            requestTvShowsByGenreIdUseCase
        )
    }

    @Test
    fun `State is updated with current cached content immediately`() = runTest {
        vm.state.test {
            assertEquals(UiState(), awaitItem())
            assertEquals(UiState(animationTvShows = animationTvShows), awaitItem())
            assertEquals(UiState(animationTvShows = animationTvShows,
                dramaTvShows = dramaTvShows),
                awaitItem())
            cancel()
        }
    }

    @Test
    fun `Progress is shown when screen starts and hidden when it finishes requesting tvShows`() =
        runTest {
            vm.getPrograms()

            vm.state.test {
                assertEquals(UiState(), awaitItem())
                assertEquals(UiState(animationTvShows = animationTvShows), awaitItem())
                assertEquals(
                    UiState(
                        animationTvShows = animationTvShows,
                        dramaTvShows = dramaTvShows
                    ),
                    awaitItem()
                )
                assertEquals(
                    UiState(
                        animationTvShows = animationTvShows,
                        dramaTvShows = dramaTvShows,
                        loading = true
                    ),
                    awaitItem()
                )
                assertEquals(
                    UiState(
                        animationTvShows = animationTvShows,
                        dramaTvShows = dramaTvShows,
                        loading = false
                    ),
                    awaitItem())
                cancel()
            }
        }

    @Test
    fun `Animation and drama tvShows are requested when UI screen starts`() = runTest {
        vm.getPrograms()
        runCurrent()

        verify(requestTvShowsByGenreIdUseCase).invoke(false,
            Constants.ANIMATION_GENRE_ID,
            ProgramGenre.ANIMATION)
        verify(requestTvShowsByGenreIdUseCase).invoke(false,
            Constants.DRAMA_GENRE_ID,
            ProgramGenre.DRAMA)
    }

}
