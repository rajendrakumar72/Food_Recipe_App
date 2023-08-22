package com.mrkumar.foodrecipieapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mrkumar.foodrecipieapp.R
import com.mrkumar.foodrecipieapp.adapters.MealCategoriesAdapter
import com.mrkumar.foodrecipieapp.adapters.MostPopularAdapter
import com.mrkumar.foodrecipieapp.databinding.FragmentHomeBinding
import com.mrkumar.foodrecipieapp.model.CategoryMeal
import com.mrkumar.foodrecipieapp.model.Meal
import com.mrkumar.foodrecipieapp.ui.activity.CategoryMealActivity
import com.mrkumar.foodrecipieapp.ui.activity.MainActivity
import com.mrkumar.foodrecipieapp.ui.activity.MealActivity
import com.mrkumar.foodrecipieapp.ui.fragment.bottomsheetmenu.MealBottomSheetFragment
import com.mrkumar.foodrecipieapp.viewmodel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMVVM:HomeViewModel
    private lateinit var randomMeal:Meal
    private lateinit var mostPopularAdapter: MostPopularAdapter
    private lateinit var categoryAdapter: MealCategoriesAdapter

    companion object{
        val MEAL_ID="Meal Id"
        val MEAL_NAME="Meal Name"
        val MEAL_THUMB="Meal Thumb"
        val CATEGORY_NAME="category Name"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)
        homeMVVM=(activity as MainActivity).viewModel
        mostPopularAdapter= MostPopularAdapter()
        categoryAdapter=MealCategoriesAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemAdapter()
        prepareCategoriesAdapter()


        homeMVVM.getMealData()
        observeRandomMealLiveData()

        homeMVVM.getPopularFood()
        observePopularMealLiveData()
        goToMealDetailActivity()

        onPopularItemClick()

        homeMVVM.getCategoryMeal()
        observeCategoriesLiveData()

        onCategoryClick()

        onPopularItemLongClick()

        onSearchIconClick()

    }

    private fun onSearchIconClick() {
        binding.imgSearch.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onPopularItemLongClick() {
        mostPopularAdapter.onLogItemClick={meal->
            val mealBottomSheetFragment =MealBottomSheetFragment.newInstance(meal.idMeal)
            mealBottomSheetFragment.show(childFragmentManager,"Meal Info")
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemClick={category->
            val intent=Intent(activity,CategoryMealActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesAdapter() {
        binding.recyclerView.apply {
            layoutManager =GridLayoutManager(activity,3,GridLayoutManager.VERTICAL,false)
            adapter=categoryAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        homeMVVM.observeMealCategoryList().observe(viewLifecycleOwner
        ) {mealList ->
          categoryAdapter.setCategoryList(mealList)

        }
    }

    private fun onPopularItemClick() {
        mostPopularAdapter.onItemClick={ meal ->
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemAdapter() {
        binding.recViewMealsPopular.apply {
            layoutManager =LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter=mostPopularAdapter
        }
    }

    private fun observePopularMealLiveData() {
        homeMVVM.observePopularMeaLiveData().observe(viewLifecycleOwner
        ) { meallist ->
           mostPopularAdapter.setMeals(mealList = meallist as ArrayList<CategoryMeal>)
        }
    }

    private fun goToMealDetailActivity() {
        binding.imgRandomMeal.setOnClickListener{
            val intent=Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)
        }

    }

    private fun observeRandomMealLiveData() {
        homeMVVM.observeMealLiveDat().observe(viewLifecycleOwner,object :Observer<Meal>{
            override fun onChanged(data: Meal) {
                Glide.with(this@HomeFragment)
                    .load(data.strMealThumb)
                    .into(binding.imgRandomMeal)

                this@HomeFragment.randomMeal=data
            }

        })
    }
}