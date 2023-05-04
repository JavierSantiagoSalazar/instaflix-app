package com.javierestudio.usecases.tvshow

import com.javierestudio.data.common.Constants
import com.javierestudio.data.repository.tvshow.TvShowRepository
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
class RequestTvShowsByGenreIdUseCaseTest {

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    private lateinit var requestTvShowsByGenreIdUseCase: RequestTvShowsByGenreIdUseCase

    @Before
    fun setup() {
        requestTvShowsByGenreIdUseCase = RequestTvShowsByGenreIdUseCase(tvShowRepository)
    }

    @Test
    fun `Given isRefreshing true and animation genre when invoke calls should call TvShowRepository`(): Unit =
        runTest {
            requestTvShowsByGenreIdUseCase(
                isRefreshing = true,
                genreId = Constants.ANIMATION_GENRE_ID,
                programGenre = ProgramGenre.ANIMATION
            )

            verify(tvShowRepository).requestTvShowByGenreId(
                isRefreshing = true,
                genreId = Constants.ANIMATION_GENRE_ID,
                programGenre = ProgramGenre.ANIMATION
            )
        }

    @Test
    fun `Given isRefreshing false and animation genre when invoke calls should call TvShowRepository`(): Unit =
        runTest {
            requestTvShowsByGenreIdUseCase(
                isRefreshing = false,
                genreId = Constants.ANIMATION_GENRE_ID,
                programGenre = ProgramGenre.ANIMATION
            )

            verify(tvShowRepository).requestTvShowByGenreId(
                isRefreshing = false,
                genreId = Constants.ANIMATION_GENRE_ID,
                programGenre = ProgramGenre.ANIMATION
            )
        }

    @Test
    fun `Given isRefreshing true and drama genre when invoke calls should call TvShowRepository`(): Unit =
        runTest {
            requestTvShowsByGenreIdUseCase(
                isRefreshing = true,
                genreId = Constants.DRAMA_GENRE_ID,
                programGenre = ProgramGenre.DRAMA
            )

            verify(tvShowRepository).requestTvShowByGenreId(
                isRefreshing = true,
                genreId = Constants.DRAMA_GENRE_ID,
                programGenre = ProgramGenre.DRAMA
            )
        }

    @Test
    fun `Given isRefreshing false and drama genre when invoke calls movies repository should call moviesRepository`(): Unit =
        runTest {
            requestTvShowsByGenreIdUseCase(
                isRefreshing = false,
                genreId = Constants.DRAMA_GENRE_ID,
                programGenre = ProgramGenre.DRAMA
            )

            verify(tvShowRepository).requestTvShowByGenreId(
                isRefreshing = false,
                genreId = Constants.DRAMA_GENRE_ID,
                programGenre = ProgramGenre.DRAMA
            )
        }
}
