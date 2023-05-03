package com.javierestudio.instaflixapp.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.FragmentDetailBinding
import com.javierestudio.instaflixapp.ui.common.launchAndCollect
import com.javierestudio.instaflixapp.ui.common.showSnackBarFunctionality
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: DetailViewModel by viewModels()

    private lateinit var detailState: DetailState

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDetailBinding.bind(view)

        with(binding) {
            binding.programDetailToolbar.setNavigationOnClickListener { findNavController().popBackStack() }
            download.setOnClickListener { it.showSnackBarFunctionality() }
            share.setOnClickListener { it.showSnackBarFunctionality() }
        }

        detailState = buildDetailState()
        detailState.setActivityComponents(visibility = false)

        viewLifecycleOwner.launchAndCollect(viewModel.state) { state ->
            if (state.movie != null) {
                detailState.setDetailComponentsForMovie(binding, state.movie)
            }
            if (state.tvShow != null) {
                detailState.setDetailComponentsForTvShow(binding, state.tvShow)
            }
        }
    }

    override fun onDestroy() {
        detailState.setActivityComponents(visibility = true)
        super.onDestroy()
    }

}
