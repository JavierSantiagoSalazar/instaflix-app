package com.javierestudio.usecases.tvshow

import com.javierestudio.data.repository.tvshow.TvShowRepository
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
class RequestPopularTvShowsUseCaseTest {

    @Mock
    private lateinit var tvShowRepository: TvShowRepository

    private lateinit var requestPopularTvShowsUseCase: RequestPopularTvShowsUseCase

    @Before
    fun setup() {
        requestPopularTvShowsUseCase = RequestPopularTvShowsUseCase(tvShowRepository)
    }

    @Test
    fun `Given isRefreshing true when invoke calls tvShow repository should call TvShowRepository`(): Unit =
        runTest {
            requestPopularTvShowsUseCase(true)

            verify(tvShowRepository).requestPopularTvShows(true)
        }

    @Test
    fun `Given isRefreshing false when invoke calls tvShow repository should call TvShowRepository`(): Unit =
        runTest {
            requestPopularTvShowsUseCase(false)

            verify(tvShowRepository).requestPopularTvShows(false)
        }
}
