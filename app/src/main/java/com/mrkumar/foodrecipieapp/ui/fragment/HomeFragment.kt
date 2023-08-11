package com.mrkumar.foodrecipieapp.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mrkumar.foodrecipieapp.R
import com.mrkumar.foodrecipieapp.databinding.FragmentHomeBinding
import com.mrkumar.foodrecipieapp.pojo.Meal
import com.mrkumar.foodrecipieapp.pojo.MealList
import com.mrkumar.foodrecipieapp.retrofit.RetrofitInstance
import com.mrkumar.foodrecipieapp.ui.activity.MealActivity
import com.mrkumar.foodrecipieapp.viewmodel.HomeViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMVVM:HomeViewModel
    private lateinit var randomMeal:Meal

    companion object{
        val MEAL_ID="Meal Id"
        val MEAL_NAME="Meal Name"
        val MEAL_THUMB="Meal Thumb"
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentHomeBinding.inflate(inflater,container,false)

        homeMVVM=ViewModelProviders.of(this)[HomeViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeMVVM.getMealData()
        observeRandomMealLiveData()

        goToMealDetailActivity()

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