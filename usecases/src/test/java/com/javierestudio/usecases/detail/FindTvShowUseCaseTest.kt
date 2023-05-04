package com.javierestudio.usecases.detail

import com.javierestudio.data.repository.tvshow.TvShowRepository
import com.javierestudio.testshared.sampleTvShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class FindTvShowUseCaseTest {

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    @Test
    fun `Given FindTvShowUseCase when Invoke calls TvShowRepository then return MoviesFlow`(): Unit =
        runTest {
            val findTvShowUseCase = FindTvShowUseCase(tvShowRepository)
            val tvShow = flowOf(sampleTvShow.copy(id = 1))
            whenever(tvShowRepository.findById(1)).thenReturn(tvShow)

            val result = findTvShowUseCase(1)

            assertEquals(tvShow, result)
        }
}
