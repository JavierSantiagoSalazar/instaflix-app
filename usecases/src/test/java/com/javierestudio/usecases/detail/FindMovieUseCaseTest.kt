package com.javierestudio.usecases.detail

import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.testshared.sampleMovie
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
class FindMovieUseCaseTest {

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    @Test
    fun `Given FindMoviesUseCase when Invoke calls MoviesRepository then return MoviesFlow`(): Unit =
        runTest {
            val findMovieUseCase = FindMovieUseCase(moviesRepository)
            val movie = flowOf(sampleMovie.copy(id = 1))
            whenever(moviesRepository.findById(1)).thenReturn(movie)

            val result = findMovieUseCase(1)

            assertEquals(movie, result)
        }
}
