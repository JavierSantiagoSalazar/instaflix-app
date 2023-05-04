package com.javierestudio.instaflixapp.ui.movies

import app.cash.turbine.test
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.apptestshared.buildDatabaseMovies
import com.javierestudio.apptestshared.buildMovieRepositoryWith
import com.javierestudio.apptestshared.buildRemoteMovies
import com.javierestudio.instaflixapp.data.database.movie.Movie
import com.javierestudio.instaflixapp.data.server.movies.RemoteMovie
import com.javierestudio.instaflixapp.testrules.CoroutinesTestRule
import com.javierestudio.instaflixapp.ui.movies.MoviesViewModel.UiState
import com.javierestudio.usecases.movie.GetMoviesByGenreUseCase
import com.javierestudio.usecases.movie.RequestMoviesByGenreIdUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieIntegrationTest {

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @Test
    fun `data is loaded from server when local source is empty`() = runTest {
        val remoteActionMoviesData = buildRemoteMovies(4, 5, 6)
        val vm = buildViewModelWith(moviesRemoteData = remoteActionMoviesData)

        vm.getPrograms(false)

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())
            Assert.assertEquals(UiState(actionMovies = emptyList()), awaitItem())
            Assert.assertEquals(UiState(actionMovies = emptyList(),
                comedyMovies = emptyList(),
                loading = false),
                awaitItem())
            Assert.assertEquals(UiState(actionMovies = emptyList(),
                comedyMovies = emptyList(),
                loading = true),
                awaitItem())
            Assert.assertEquals(UiState(actionMovies = emptyList(),
                comedyMovies = emptyList(),
                loading = false),
                awaitItem())

            val actionMovies = awaitItem().actionMovies!!
            Assert.assertEquals("Title 4", actionMovies[0].title)
            Assert.assertEquals("Title 5", actionMovies[1].title)
            Assert.assertEquals("Title 6", actionMovies[2].title)
            cancel()
        }
    }

    @Test
    fun `data is loaded from local source when available`() = runTest {
        val localMoviesData = buildDatabaseMovies(1, 2, 3, genre = ProgramGenre.ACTION)
        val remoteMoviesData = buildRemoteMovies(4, 5, 6)

        val vm = buildViewModelWith(
            moviesLocalData = localMoviesData,
            moviesRemoteData = remoteMoviesData
        )

        vm.state.test {
            Assert.assertEquals(UiState(), awaitItem())

            val movies = awaitItem().actionMovies!!
            Assert.assertEquals("Title 1", movies[0].title)
            Assert.assertEquals("Title 2", movies[1].title)
            Assert.assertEquals("Title 3", movies[2].title)
            Assert.assertEquals(awaitItem().comedyMovies!!.size, 0)
            cancel()
        }
    }

    private fun buildViewModelWith(
        moviesLocalData: List<Movie> = emptyList(),
        moviesRemoteData: List<RemoteMovie> = emptyList(),
    ): MoviesViewModel {
        val moviesRepository = buildMovieRepositoryWith(moviesLocalData, moviesRemoteData)
        val getMoviesByGenreUseCase = GetMoviesByGenreUseCase(moviesRepository)
        val requestPopularMoviesUseCase = RequestMoviesByGenreIdUseCase(moviesRepository)
        return MoviesViewModel(getMoviesByGenreUseCase, requestPopularMoviesUseCase)
    }
}
