package com.mrkumar.foodrecipieapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mrkumar.foodrecipieapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mrkumar.foodrecipieapp.database.MealDatabase
import com.mrkumar.foodrecipieapp.databinding.ActivityMainBinding
import com.mrkumar.foodrecipieapp.viewmodel.HomeViewModel
import com.mrkumar.foodrecipieapp.viewmodel.HomeViewModelFactory

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val viewModel:HomeViewModel by lazy {
        val mealDatabase =MealDatabase.getInstance(this)
        val homeVMFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeVMFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigation=findViewById<BottomNavigationView>(R.id.bottomNav)
        val navController=Navigation.findNavController(this, R.id.fragContainer)

        NavigationUI.setupWithNavController(bottomNavigation,navController)

    }
}