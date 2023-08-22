package com.mrkumar.foodrecipieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mrkumar.foodrecipieapp.database.MealDatabase

class MealDetailViewModelFactory(private val mealDatabase: MealDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MealDetailViewModel(mealDatabase) as T
    }
}