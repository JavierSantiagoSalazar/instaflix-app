package com.javierestudio.usecases.tvshow

import com.javierestudio.data.repository.tvshow.TvShowRepository
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.testshared.sampleTvShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetTvShowByGenreUseCaseTest {

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    private lateinit var getTvShowByGenreUseCase: GetTvShowByGenreUseCase

    @Before
    fun setup() {
        getTvShowByGenreUseCase = GetTvShowByGenreUseCase(tvShowRepository)
    }

    @Test
    fun `Given DRAMA genre when GetTvShowByGenreUseCase is called should returns dramaTvShows`(): Unit =
        runTest {
            val dramaTvShows =
                flowOf(listOf(sampleTvShow.copy(id = 1, programGenre = ProgramGenre.DRAMA)))
            whenever(tvShowRepository.dramaTvShows).thenReturn(dramaTvShows)


            val result = getTvShowByGenreUseCase(ProgramGenre.DRAMA)

            assertEquals(dramaTvShows, result)
        }

    @Test
    fun `Given ANIMATION genre when GetTvShowByGenreUseCase is called should returns animationTvShows`(): Unit =
        runTest {
            val animationTvShows =
                flowOf(listOf(sampleTvShow.copy(id = 1, programGenre = ProgramGenre.COMEDY)))
            whenever(tvShowRepository.animationTvShows).thenReturn(animationTvShows)


            val result = getTvShowByGenreUseCase(ProgramGenre.ANIMATION)

            assertEquals(animationTvShows, result)
        }

    @Test
    fun `Given POPULAR genre when GetTvShowByGenreUseCase is called should returns popularTvShows`(): Unit =
        runTest {
            val popularTvShows =
                flowOf(listOf(sampleTvShow.copy(id = 1, programGenre = ProgramGenre.POPULAR)))
            whenever(tvShowRepository.popularTvShows).thenReturn(popularTvShows)


            val result = getTvShowByGenreUseCase(ProgramGenre.POPULAR)

            assertEquals(popularTvShows, result)
        }
}