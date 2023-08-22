package com.mrkumar.foodrecipieapp.ui.fragment.bottomsheetmenu

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mrkumar.foodrecipieapp.R
import com.mrkumar.foodrecipieapp.databinding.FragmentMealBottomSheetBinding
import com.mrkumar.foodrecipieapp.ui.activity.MainActivity
import com.mrkumar.foodrecipieapp.ui.activity.MealActivity
import com.mrkumar.foodrecipieapp.ui.fragment.HomeFragment
import com.mrkumar.foodrecipieapp.viewmodel.HomeViewModel


private const val mealId = "param1"


class MealBottomSheetFragment : BottomSheetDialogFragment() {
    private var mealId: String? = null
    private lateinit var binding: FragmentMealBottomSheetBinding
    private lateinit var viewModel: HomeViewModel
    private var mealThumb:String?=null
    private var mealName:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mealId = it.getString(mealId)
        }
        viewModel=(activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMealBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mealId?.let {
            viewModel.getMealById(it)
        }

        observeBottomSheetMeal()

        onBottomSheetClick()
    }

    private fun onBottomSheetClick() {
        binding.bottomSheetParent.setOnClickListener {
            if (mealId !=null && mealThumb!=null){
                val intent=Intent(activity,MealActivity::class.java)
                intent.apply {
                    putExtra(HomeFragment.MEAL_ID,mealId)
                    putExtra(HomeFragment.MEAL_NAME,mealName)
                    putExtra(HomeFragment.MEAL_THUMB,mealThumb)
                }
                startActivity(intent)
            }
        }
    }

    private fun observeBottomSheetMeal() {
        viewModel.observeBottomSheetLiveData().observe(viewLifecycleOwner) { meal ->
            Glide.with(this).load(meal.strMealThumb).into(binding.imgCategory)
            binding.tvMealCountry.text=meal.strArea
            binding.tvMealNameInBtmsheet.text=meal.strMeal
            binding.tvMealCategory.text =meal.strCategory

            mealName=meal.strMeal
            mealThumb=meal.strMealThumb
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            MealBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(mealId, param1)
                }
            }
    }
}