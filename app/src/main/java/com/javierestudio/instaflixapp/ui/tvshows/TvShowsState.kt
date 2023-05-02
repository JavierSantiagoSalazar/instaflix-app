package com.javierestudio.instaflixapp.ui.tvshows

import android.app.Activity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.javierestudio.domain.Movie
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.R
import kotlinx.coroutines.CoroutineScope
import kotlin.math.acosh

fun Fragment.buildTvShowsFragment(
    activity: Activity = requireActivity(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
) = TvShowsState(activity, scope, navController)

class TvShowsState(
    private val activity: Activity,
    private val scope: CoroutineScope,
    private val navController: NavController,
) {

    fun onMovieClicked(movie: TvShow) {
        //val action = HomeFragmentDirections.actionMainToDetail(movie.id)
        //navController.navigate(action)
    }

    fun setToolbarTitle() {
        activity.findViewById<TextView>(R.id.tvToolbarTitle).run {
            this.text = context.getString(R.string.tv_tv_show_toolbar_title)
        }
    }

}
