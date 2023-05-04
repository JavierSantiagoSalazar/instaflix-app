package com.javierestudio.usecases.movie

import com.javierestudio.data.repository.movie.MoviesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RequestPopularMoviesUseCaseTest {

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var requestPopularMoviesUseCase: RequestPopularMoviesUseCase

    @Before
    fun setup() {
        requestPopularMoviesUseCase = RequestPopularMoviesUseCase(moviesRepository)
    }

    @Test
    fun `Given isRefreshing true when invoke calls movies repository should call moviesRepository`(): Unit =
        runTest {
            requestPopularMoviesUseCase(true)

            verify(moviesRepository).requestPopularMovies(true)
        }

    @Test
    fun `Given isRefreshing false when invoke calls movies repository should call moviesRepository`(): Unit =
        runTest {
            requestPopularMoviesUseCase(false)

            verify(moviesRepository).requestPopularMovies(false)
        }
}
