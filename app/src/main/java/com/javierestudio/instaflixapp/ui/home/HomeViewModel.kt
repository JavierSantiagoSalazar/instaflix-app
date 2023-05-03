package com.javierestudio.instaflixapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierestudio.domain.Error
import com.javierestudio.domain.Movie
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.data.toError
import com.javierestudio.usecases.movie.GetPopularMoviesUseCase
import com.javierestudio.usecases.movie.RequestPopularMoviesUseCase
import com.javierestudio.usecases.tvshow.GetPopularTvShowsUseCase
import com.javierestudio.usecases.tvshow.RequestPopularTvShowsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPopularMoviesUseCase: GetPopularMoviesUseCase,
    getPopularTvShowsUseCase: GetPopularTvShowsUseCase,
    private val requestPopularMoviesUseCase: RequestPopularMoviesUseCase,
    private val requestPopularTvShowsUseCase: RequestPopularTvShowsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getPopularMoviesUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { movies -> _state.update { it.copy(movies = movies) } }
        }
        viewModelScope.launch {
            getPopularTvShowsUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { tvShows -> _state.update { it.copy(tvShows = tvShows) } }
        }
    }

    fun getPrograms(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val moviesError = requestPopularMoviesUseCase(isRefreshing)
            val tvShowsError = requestPopularTvShowsUseCase(isRefreshing)
            _state.value = _state.value.copy(loading = false, error = moviesError)
            _state.value = _state.value.copy(loading = false, error = tvShowsError)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val movies: List<Movie>? = null,
        val tvShows: List<TvShow>? = null,
        val error: Error? = null
    )
}
