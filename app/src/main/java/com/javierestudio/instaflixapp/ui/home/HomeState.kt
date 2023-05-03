package com.javierestudio.instaflixapp.ui.home

import android.Manifest
import android.app.Activity
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.javierestudio.domain.Movie
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildHomeState(
    activity: Activity = requireActivity(),
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        fragment = this,
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    ),
) = HomeState(activity, scope, navController, locationPermissionRequester)

class HomeState(
    private val activity: Activity,
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: PermissionRequester,
) {

    fun onMovieClicked(movie: Movie) {
        val action = HomeFragmentDirections.actionHomeToDetail(movie.programType, movie.id)
        navController.navigate(action)
    }

    fun onTvShowClicked(tvShow: TvShow) {
        val action = HomeFragmentDirections.actionHomeToDetail(tvShow.programType, tvShow.id)
        navController.navigate(action)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }

    fun setToolbarTitle() {
        activity.findViewById<TextView>(R.id.tvToolbarTitle).run {
            this.text = context.getString(R.string.tv_home_toolbar_title)
        }
    }
}
