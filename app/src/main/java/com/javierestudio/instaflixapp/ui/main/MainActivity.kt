package com.javierestudio.instaflixapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.javierestudio.instaflixapp.R
import com.javierestudio.instaflixapp.databinding.ActivityMainBinding
import com.javierestudio.instaflixapp.ui.common.showSnackBarFunctionality
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNav = mBinding.bottomNavigationView

        NavigationUI.setupWithNavController(bottomNav, navController)

        mBinding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    mBinding.root.showSnackBarFunctionality()
                    true
                }
                else -> false
            }
        }
    }
}
