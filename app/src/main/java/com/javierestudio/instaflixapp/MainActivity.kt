package com.javierestudio.instaflixapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.setupActionBarWithNavController
import com.javierestudio.instaflixapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)


        mBinding.toolbar.setOnMenuItemClickListener {menuItem ->
            when (menuItem.itemId) {
                R.id.action_settings -> {
                    println("Incoming action")
                    true
                }
                else -> false
            }
        }

    }
}
