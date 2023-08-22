package com.mrkumar.foodrecipieapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mrkumar.foodrecipieapp.R
import com.mrkumar.foodrecipieapp.adapters.MealCategoriesAdapter
import com.mrkumar.foodrecipieapp.databinding.FragmentCategoriesBinding
import com.mrkumar.foodrecipieapp.ui.activity.CategoryMealActivity
import com.mrkumar.foodrecipieapp.ui.activity.MainActivity
import com.mrkumar.foodrecipieapp.viewmodel.HomeViewModel

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var categoriesAdapter: MealCategoriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentCategoriesBinding.inflate(layoutInflater)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        observeCategories()
        onCategoryClick()
    }

    private fun observeCategories() {
        viewModel.observeMealCategoryList().observe(viewLifecycleOwner) { categories ->
            categoriesAdapter.setCategoryList(categories)

        }
    }

    private fun setupRecyclerView() {
        categoriesAdapter = MealCategoriesAdapter()
        binding.rvCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter= categoriesAdapter
        }
    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick={category->
            val intent= Intent(activity, CategoryMealActivity::class.java)
            intent.putExtra(HomeFragment.CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }
}