package com.mrkumar.foodrecipieapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.mrkumar.foodrecipieapp.adapters.CategoryMealAdapter
import com.mrkumar.foodrecipieapp.databinding.ActivityCategoryMealBinding
import com.mrkumar.foodrecipieapp.ui.fragment.HomeFragment
import com.mrkumar.foodrecipieapp.viewmodel.CategoryMealsViewModel

class CategoryMealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryMealBinding
    private lateinit var categoryMealsViewModel: CategoryMealsViewModel

    private lateinit var categoryMealAdapter:CategoryMealAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCategoryMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        categoryMealsViewModel=ViewModelProviders.of(this)[CategoryMealsViewModel::class.java]

        prepareRVForMeal()
        intent.getStringExtra(HomeFragment.CATEGORY_NAME)
            ?.let { categoryMealsViewModel.getMealsByCategory(it) }

        categoryMealsViewModel.observerCategoryLiveData().observe(this
        ) {categoryMeal->
            binding.tvCategoryCount.text = categoryMeal.size.toString()
            categoryMealAdapter.setMealList(categoryMeal)
        }
    }

    private fun prepareRVForMeal() {
        categoryMealAdapter= CategoryMealAdapter()
        binding.mealRecyclerview.apply{
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=categoryMealAdapter
        }
    }
}