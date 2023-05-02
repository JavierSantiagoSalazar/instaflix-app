package com.javierestudio.instaflixapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentHomeBinding
import com.javierestudio.instaflixapp.ui.common.launchAndCollect
import com.javierestudio.instaflixapp.ui.common.setVisibleOrGone
import com.javierestudio.instaflixapp.ui.common.showErrorSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var homeState: HomeState

    private val moviesAdapter = MoviesAdapter { homeState.onMovieClicked(it) }
    private val tvShowsAdapter = TvShowsAdapter { homeState.onTvShowClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view).apply {
            recyclerMovies.adapter = moviesAdapter
            recyclerSeries.adapter = tvShowsAdapter
        }

        homeState = buildHomeState().apply { setToolbarTitle() }

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.progress.setVisibleOrGone(it.loading)
            moviesAdapter.submitList(it.movies)
            tvShowsAdapter.submitList(it.tvShows)
            it.error?.let { error ->
                view.showErrorSnackBar(error) {
                    viewModel.onUiReady()
                }
            }
        }

        homeState.requestLocationPermission {
            viewModel.onUiReady()
        }
    }
}
