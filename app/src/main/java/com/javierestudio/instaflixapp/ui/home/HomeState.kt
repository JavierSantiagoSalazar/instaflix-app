package com.javierestudio.instaflixapp.ui.home

import android.Manifest
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.javierestudio.domain.Movie
import com.javierestudio.domain.TvShow
import com.javierestudio.instaflixapp.ui.common.PermissionRequester
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.buildHomeState(
    scope: CoroutineScope = viewLifecycleOwner.lifecycleScope,
    navController: NavController = findNavController(),
    locationPermissionRequester: PermissionRequester = PermissionRequester(
        fragment = this,
        permission = Manifest.permission.ACCESS_COARSE_LOCATION
    ),
) = HomeState(scope, navController, locationPermissionRequester)

class HomeState(
    private val scope: CoroutineScope,
    private val navController: NavController,
    private val locationPermissionRequester: PermissionRequester,
) {

    fun onMovieClicked(movie: Movie) {
        //val action = HomeFragmentDirections.actionMainToDetail(movie.id)
        //navController.navigate(action)
    }

    fun onTvShowClicked(tvShow: TvShow) {
        //val action = HomeFragmentDirections.actionMainToDetail(movie.id)
        //navController.navigate(action)
    }

    fun requestLocationPermission(afterRequest: (Boolean) -> Unit) {
        scope.launch {
            val result = locationPermissionRequester.request()
            afterRequest(result)
        }
    }
}
