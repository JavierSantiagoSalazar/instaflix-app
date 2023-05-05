package com.javierestudio.instaflixapp.ui.tvshows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.javierestudio.data.common.Constants.ANIMATION_GENRE_ID
import com.javierestudio.data.common.Constants.DRAMA_GENRE_ID
import com.javierestudio.domain.Error
import com.javierestudio.domain.ProgramGenre
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.data.toError
import com.javierestudio.instaflixapp.ui.common.networkhelper.NetworkHelper
import com.javierestudio.usecases.tvshow.GetTvShowByGenreUseCase
import com.javierestudio.usecases.tvshow.RequestTvShowsByGenreIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowsViewModel @Inject constructor(
    getTvShowByGenreUseCase: GetTvShowByGenreUseCase,
    private val requestTvShowsByGenreIdUseCase: RequestTvShowsByGenreIdUseCase,
    private val networkHelper: NetworkHelper,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getTvShowByGenreUseCase(ProgramGenre.ANIMATION)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { animationTvShows -> _state.update { it.copy(animationTvShows = animationTvShows) } }
        }
        viewModelScope.launch {
            getTvShowByGenreUseCase(ProgramGenre.DRAMA)
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect { dramaTvShows -> _state.update { it.copy(dramaTvShows = dramaTvShows) } }
        }
    }

    fun getPrograms(isRefreshing: Boolean = false) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val animationTvShowsError = requestTvShowsByGenreIdUseCase(
                isRefreshing = isRefreshing,
                genreId = ANIMATION_GENRE_ID,
                programGenre = ProgramGenre.ANIMATION
            )
            val dramaTvShowsError = requestTvShowsByGenreIdUseCase(
                isRefreshing = isRefreshing,
                genreId = DRAMA_GENRE_ID,
                programGenre = ProgramGenre.DRAMA
            )
            _state.value = _state.value.copy(loading = false, error = animationTvShowsError)
            _state.value = _state.value.copy(loading = false, error = dramaTvShowsError)
        }
    }

    fun checkInternetConnection(action: () -> Unit) {
        viewModelScope.launch {
            if (!networkHelper.isInternetAvailable()) {
                _state.value = state.value.copy(error = Error.Connectivity)
            } else {
                action()
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val animationTvShows: List<TvShow>? = null,
        val dramaTvShows: List<TvShow>? = null,
        val error: Error? = null,
    )
}
