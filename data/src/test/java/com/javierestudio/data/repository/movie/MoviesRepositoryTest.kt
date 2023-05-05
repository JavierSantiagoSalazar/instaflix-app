package com.javierestudio.data.repository.movie

import arrow.core.right
import com.javierestudio.data.RegionRepository
import com.javierestudio.data.common.Constants
import com.javierestudio.data.datasource.movie.MovieLocalDataSource
import com.javierestudio.data.datasource.movie.MovieRemoteDataSource
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
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MoviesRepositoryTest {

    @Mock
    lateinit var localDataSource: MovieLocalDataSource

    @Mock
    lateinit var remoteDataSource: MovieRemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    private lateinit var moviesRepository: MoviesRepository

    @Before
    fun setUp() {
        moviesRepository = MoviesRepository(regionRepository, localDataSource, remoteDataSource)
    }

    @Test
    fun `Popular movies are taken from local data source if available`(): Unit = runTest {
        val localMovies = flowOf(listOf(sampleMovie.copy(1, programGenre = ProgramGenre.POPULAR)))
        whenever(localDataSource.movies).thenReturn(localMovies)

        val result = moviesRepository.popularMovies

        assertEquals(localMovies, result)
    }

    @Test
    fun `Action movies are taken from local data source if available`(): Unit = runTest {
        val localActionMovies =
            flowOf(listOf(sampleMovie.copy(1, programGenre = ProgramGenre.ACTION)))
        whenever(localDataSource.actionMovies).thenReturn(localActionMovies)

        val result = moviesRepository.actionMovies

        assertEquals(localActionMovies, result)
    }

    @Test
    fun `Comedy movies are taken from local data source if available`(): Unit = runTest {
        val localComedyMovies =
            flowOf(listOf(sampleMovie.copy(1, programGenre = ProgramGenre.COMEDY)))
        whenever(localDataSource.comedyMovies).thenReturn(localComedyMovies)

        val result = moviesRepository.comedyMovies

        assertEquals(localComedyMovies, result)
    }

    @Test
    fun `Popular movies are saved to local data source when it's empty`(): Unit = runTest {
        val remoteMovies = listOf(sampleMovie.copy(2, programGenre = ProgramGenre.POPULAR))
        whenever(localDataSource.isEmpty()).thenReturn(true)
        whenever(localDataSource.deleteMoviesByGenre(ProgramGenre.POPULAR)).thenReturn(null)
        whenever(regionRepository.findLastRegion()).thenReturn(RegionRepository.DEFAULT_REGION)
        whenever(remoteDataSource.findPopularMovies(any())).thenReturn(remoteMovies.right())

        moviesRepository.requestPopularMovies(false)

        verify(localDataSource).save(remoteMovies, ProgramGenre.POPULAR)
    }

    @Test
    fun `Action movies are saved to local data source when it's empty`(): Unit = runTest {
        val programGenre = ProgramGenre.ACTION
        val remoteMovies = listOf(sampleMovie.copy(2, programGenre = programGenre))
        whenever(localDataSource.isMoviesEmptyByGenreId(programGenre)).thenReturn(true)
        whenever(localDataSource.deleteMoviesByGenre(programGenre)).thenReturn(null)
        whenever(remoteDataSource.findMoviesByGenre(Constants.ACTION_GENRE_ID)).thenReturn(
            remoteMovies.right())

        moviesRepository.requestMovieByGenreId(
            isRefreshing = false,
            genreId = Constants.ACTION_GENRE_ID,
            programGenre = programGenre
        )

        verify(localDataSource).save(remoteMovies, programGenre)
    }

    @Test
    fun `Comedy movies are saved to local data source when it's empty`(): Unit = runTest {
        val programGenre = ProgramGenre.COMEDY
        val remoteMovies = listOf(sampleMovie.copy(2, programGenre = programGenre))
        whenever(localDataSource.isMoviesEmptyByGenreId(programGenre)).thenReturn(true)
        whenever(localDataSource.deleteMoviesByGenre(programGenre)).thenReturn(null)
        whenever(remoteDataSource.findMoviesByGenre(Constants.COMEDY_GENRE_ID)).thenReturn(
            remoteMovies.right())

        moviesRepository.requestMovieByGenreId(
            isRefreshing = false,
            genreId = Constants.COMEDY_GENRE_ID,
            programGenre = programGenre
        )

        verify(localDataSource).save(remoteMovies, programGenre)
    }

    @Test
    fun `Finding a movie by id is done in local data source`(): Unit = runTest {
        val movie = flowOf(sampleMovie.copy(id = 5))
        whenever(localDataSource.findById(5)).thenReturn(movie)

        val result = moviesRepository.findById(5)

        assertEquals(movie, result)
    }
}
