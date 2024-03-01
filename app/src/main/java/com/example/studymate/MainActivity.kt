package com.example.studymate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.example.studymate.databinding.ActivityMainBinding
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val nav by lazy { supportFragmentManager.findFragmentById(R.id.navHost)!!.findNavController() }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.insertFragment,
                R.id.listFragment,
                R.id.updateFragment,
            )
        )
    }

    // TODO(A): Load view models


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(nav, appBarConfiguration)

        // TODO(B): Initialize view models

    }

    override fun onSupportNavigateUp(): Boolean {
        return nav.navigateUp() || super.onSupportNavigateUp()
    }
}