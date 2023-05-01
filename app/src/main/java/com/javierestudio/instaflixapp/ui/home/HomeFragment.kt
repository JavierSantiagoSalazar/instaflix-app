package com.javierestudio.instaflixapp.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentHomeBinding
import com.javierestudio.instaflixapp.ui.common.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var homeState: HomeState

    private val moviesAdapter = MoviesAdapter { homeState.onMovieClicked(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view).apply {
            recyclerMovies.adapter = moviesAdapter
        }

        homeState = buildHomeState()

        viewLifecycleOwner.launchAndCollect(viewModel.state) {
            binding.progress.setVisibleOrGone(it.loading)
            moviesAdapter.submitList(it.movies)
            it.error?.let { error ->
                view.showErrorSnackBar(error) {
                    viewModel.onUiReady()
                }
            }
        }

        homeState.requestLocationPermission {
            viewModel.onUiReady()
        }

        requireActivity().findViewById<TextView>(R.id.tvToolbarTitle).run {
            this.text = context.getString(R.string.tv_home_toolbar_title)
        }
    }
}
