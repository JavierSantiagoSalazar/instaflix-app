package com.javierestudio.usecases.movie

import com.javierestudio.data.common.Constants
import com.javierestudio.data.repository.movie.MoviesRepository
import com.javierestudio.domain.ProgramGenre
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
class RequestMoviesByGenreIdUseCaseTest {

    @Mock
    private lateinit var moviesRepository: MoviesRepository

    private lateinit var requestMoviesByGenreIdUseCase: RequestMoviesByGenreIdUseCase

    @Before
    fun setup() {
        requestMoviesByGenreIdUseCase = RequestMoviesByGenreIdUseCase(moviesRepository)
    }

    @Test
    fun `Given isRefreshing true and action genre when invoke calls should call moviesRepository`(): Unit =
        runTest {
            requestMoviesByGenreIdUseCase(
                isRefreshing = true,
                genreId = Constants.ACTION_GENRE_ID,
                programGenre = ProgramGenre.ACTION
            )

            verify(moviesRepository).requestMovieByGenreId(
                isRefreshing = true,
                genreId = Constants.ACTION_GENRE_ID,
                programGenre = ProgramGenre.ACTION
            )
        }

    @Test
    fun `Given isRefreshing false and action genre when invoke calls should call moviesRepository`(): Unit =
        runTest {
            requestMoviesByGenreIdUseCase(
                isRefreshing = false,
                genreId = Constants.ACTION_GENRE_ID,
                programGenre = ProgramGenre.ACTION
            )

            verify(moviesRepository).requestMovieByGenreId(
                isRefreshing = false,
                genreId = Constants.ACTION_GENRE_ID,
                programGenre = ProgramGenre.ACTION
            )
        }

    @Test
    fun `Given isRefreshing true and comedy genre when invoke calls should call moviesRepository`(): Unit =
        runTest {
            requestMoviesByGenreIdUseCase(
                isRefreshing = true,
                genreId = Constants.COMEDY_GENRE_ID,
                programGenre = ProgramGenre.COMEDY
            )

            verify(moviesRepository).requestMovieByGenreId(
                isRefreshing = true,
                genreId = Constants.COMEDY_GENRE_ID,
                programGenre = ProgramGenre.COMEDY
            )
        }

    @Test
    fun `Given isRefreshing false and comedy genre when invoke calls should call moviesRepository`(): Unit =
        runTest {
            requestMoviesByGenreIdUseCase(
                isRefreshing = false,
                genreId = Constants.COMEDY_GENRE_ID,
                programGenre = ProgramGenre.COMEDY
            )

            verify(moviesRepository).requestMovieByGenreId(
                isRefreshing = false,
                genreId = Constants.COMEDY_GENRE_ID,
                programGenre = ProgramGenre.COMEDY
            )
        }
}
