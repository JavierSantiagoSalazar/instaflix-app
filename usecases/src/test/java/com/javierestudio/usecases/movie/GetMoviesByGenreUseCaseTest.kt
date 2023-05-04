package com.javierestudio.usecases.movie

import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.testshared.sampleMovie
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
class GetMoviesByGenreUseCaseTest {

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var getMoviesByGenreUseCase: GetMoviesByGenreUseCase

    @Before
    fun setup() {
        getMoviesByGenreUseCase = GetMoviesByGenreUseCase(moviesRepository)
    }

    @Test
    fun `Given ACTION genre when GetMoviesByGenreUseCase is called should returns actionMovies`(): Unit =
        runTest {
            val actionMovies =
                flowOf(listOf(sampleMovie.copy(id = 1, programGenre = ProgramGenre.ACTION)))
            whenever(moviesRepository.actionMovies).thenReturn(actionMovies)


            val result = getMoviesByGenreUseCase(ProgramGenre.ACTION)

            assertEquals(actionMovies, result)
        }

    @Test
    fun `Given COMEDY genre when GetMoviesByGenreUseCase is called should returns comedyMovies`(): Unit =
        runTest {
            val comedyMovies =
                flowOf(listOf(sampleMovie.copy(id = 1, programGenre = ProgramGenre.COMEDY)))
            whenever(moviesRepository.comedyMovies).thenReturn(comedyMovies)


            val result = getMoviesByGenreUseCase(ProgramGenre.COMEDY)

            assertEquals(comedyMovies, result)
        }

    @Test
    fun `Given POPULAR genre when GetMoviesByGenreUseCase is called should returns popularMovies`(): Unit =
        runTest {
            val popularMovies =
                flowOf(listOf(sampleMovie.copy(id = 1, programGenre = ProgramGenre.POPULAR)))
            whenever(moviesRepository.popularMovies).thenReturn(popularMovies)


            val result = getMoviesByGenreUseCase(ProgramGenre.POPULAR)

            assertEquals(popularMovies, result)
        }
}