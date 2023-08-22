package com.mrkumar.foodrecipieapp.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mrkumar.foodrecipieapp.adapters.SearchMealsAdapter
import com.mrkumar.foodrecipieapp.databinding.FragmentSearchBinding
import com.mrkumar.foodrecipieapp.ui.activity.MainActivity
import com.mrkumar.foodrecipieapp.viewmodel.HomeViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var searchMealsAdapter: SearchMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel=(activity as MainActivity).viewModel

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding=FragmentSearchBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        binding.icSearch.setOnClickListener { searchMeal() }
        observeSearchMealLiveData()

        var searchJob:Job?=null
        binding.edSearch.addTextChangedListener { searchQuery->
            searchJob?.cancel()
            searchJob = lifecycleScope.launch {
                delay(500)
                viewModel.searchMealData(searchQuery.toString())
            }
        }
    }

    private fun observeSearchMealLiveData() {
        viewModel.observeSearchLiveData().observe(viewLifecycleOwner){mealsList->
            searchMealsAdapter.differ.submitList(mealsList)
        }
    }

    private fun searchMeal() {
        val searchMeal=binding.edSearch.text.toString()
        if (searchMeal.isNotBlank()){
            viewModel.searchMealData(searchMeal)
        }
    }

    private fun setUpRecyclerView() {
        searchMealsAdapter=SearchMealsAdapter()
        binding.rvSearchMeal.apply {
            layoutManager=GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter=searchMealsAdapter
        }
    }
}