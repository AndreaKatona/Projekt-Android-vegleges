package com.zoltanlorinczi.project_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.zoltanlorinczi.project_retorfit.R
import com.zoltanlorinczi.project_retorfit.databinding.ActivityMainBinding
import com.zoltanlorinczi.project_retrofit.manager.SharedPreferencesManager

class MainActivity : AppCompatActivity() {

    val TAG: String = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate() called!")
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val token = App.sharedPreferences.getStringValue(SharedPreferencesManager.KEY_TOKEN, "EMPTY")
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController : NavController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            bottomNavigationView.visibility = if(destination.id == R.id.loginFragment) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }
        if (token != "EMPTY") {
            if (token != null) {
                Log.d(TAG, "Token: $token")
                navController.navigate(R.id.listFragment)

            }

        } else {
            Log.d(TAG, "TOKEN NOT EXISTING")
            navController.navigate(R.id.loginFragment)

        }
        bottomNavigationView.setOnNavigationItemSelectedListener{
            when (it.itemId) {
                //R.id.activitiesFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.taskListFragment)
               R.id.settingsFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment2)
               R.id.taskFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.listFragment)
               R.id.groupsFragment -> findNavController(R.id.nav_host_fragment).navigate(R.id.groupFragment)
                else ->
                {

                }

            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called!")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called!")
    }
}