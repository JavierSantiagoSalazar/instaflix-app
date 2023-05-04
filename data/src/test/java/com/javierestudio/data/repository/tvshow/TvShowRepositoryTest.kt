package com.javierestudio.data.repository.tvshow

import arrow.core.right
import com.javierestudio.data.common.Constants
import com.javierestudio.data.datasource.thshow.TvShowLocalDataSource
import com.javierestudio.data.datasource.thshow.TvShowRemoteDataSource
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.testshared.sampleTvShow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TvShowRepositoryTest {

    @Mock
    lateinit var localDataSource: TvShowLocalDataSource

    @Mock
    lateinit var remoteDataSource: TvShowRemoteDataSource

    private lateinit var tvShowRepository: TvShowRepository

    @Before
    fun setUp() {
        tvShowRepository = TvShowRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `Popular TvShows are taken from local data source if available`(): Unit = runTest {
        val localTvShows = flowOf(listOf(sampleTvShow.copy(1, programGenre = ProgramGenre.POPULAR)))
        whenever(localDataSource.tvShows).thenReturn(localTvShows)

        val result = tvShowRepository.popularTvShows

        assertEquals(localTvShows, result)
    }

    @Test
    fun `Animation TvShows are taken from local data source if available`(): Unit = runTest {
        val localAnimationTvShows =
            flowOf(listOf(sampleTvShow.copy(1, programGenre = ProgramGenre.ANIMATION)))
        whenever(localDataSource.animationTvShows).thenReturn(localAnimationTvShows)

        val result = tvShowRepository.animationTvShows

        assertEquals(localAnimationTvShows, result)
    }

    @Test
    fun `Drama TvShows are taken from local data source if available`(): Unit = runTest {
        val localDramaTvShows =
            flowOf(listOf(sampleTvShow.copy(1, programGenre = ProgramGenre.DRAMA)))
        whenever(localDataSource.dramaTvShows).thenReturn(localDramaTvShows)

        val result = tvShowRepository.dramaTvShows

        assertEquals(localDramaTvShows, result)
    }

    @Test
    fun `Popular TvShows are saved to local data source when it's empty`(): Unit = runTest {
        val remoteTvShows = listOf(sampleTvShow.copy(2, programGenre = ProgramGenre.POPULAR))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(localDataSource.deleteTvShowsByGenre(ProgramGenre.POPULAR)).thenReturn(null)
        whenever(remoteDataSource.findPopularTvShows()).thenReturn(remoteTvShows.right())

        tvShowRepository.requestPopularTvShows(false)

        verify(localDataSource).save(remoteTvShows, ProgramGenre.POPULAR)
    }

    @Test
    fun `Animation TvShows are saved to local data source when it's empty`(): Unit = runTest {
        val programGenre = ProgramGenre.ANIMATION
        val remoteAnimationTvShows = listOf(sampleTvShow.copy(2, programGenre = programGenre))
        whenever(localDataSource.isTvShowsEmptyByGenreId(programGenre)).thenReturn(true)
        whenever(localDataSource.deleteTvShowsByGenre(programGenre)).thenReturn(null)
        whenever(remoteDataSource.findTvShowsByGenre(Constants.ANIMATION_GENRE_ID)).thenReturn(
            remoteAnimationTvShows.right())

        tvShowRepository.requestTvShowByGenreId(
            isRefreshing = false,
            genreId = Constants.ANIMATION_GENRE_ID,
            programGenre = programGenre
        )

        verify(localDataSource).save(remoteAnimationTvShows, programGenre)
    }

    @Test
    fun `Drama TvShows are saved to local data source when it's empty`(): Unit = runTest {
        val programGenre = ProgramGenre.DRAMA
        val remoteDramaTvShows = listOf(sampleTvShow.copy(2, programGenre = programGenre))
        whenever(localDataSource.isTvShowsEmptyByGenreId(programGenre)).thenReturn(true)
        whenever(localDataSource.deleteTvShowsByGenre(programGenre)).thenReturn(null)
        whenever(remoteDataSource.findTvShowsByGenre(Constants.DRAMA_GENRE_ID)).thenReturn(
            remoteDramaTvShows.right())

        tvShowRepository.requestTvShowByGenreId(
            isRefreshing = false,
            genreId = Constants.DRAMA_GENRE_ID,
            programGenre = programGenre
        )

        verify(localDataSource).save(remoteDramaTvShows, programGenre)
    }

    @Test
    fun `Finding a TvShow by id is done in local data source`(): Unit = runTest {
        val tvShow = flowOf(sampleTvShow.copy(id = 5))
        whenever(localDataSource.findById(5)).thenReturn(tvShow)

        val result = tvShowRepository.findById(5)

        assertEquals(tvShow, result)
    }

}
