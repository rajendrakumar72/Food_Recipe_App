package com.mrkumar.foodrecipieapp.ui.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.mrkumar.foodrecipieapp.R
import com.mrkumar.foodrecipieapp.database.MealDatabase
import com.mrkumar.foodrecipieapp.databinding.ActivityMealBinding
import com.mrkumar.foodrecipieapp.model.Meal
import com.mrkumar.foodrecipieapp.model.MealDBModel
import com.mrkumar.foodrecipieapp.ui.fragment.HomeFragment
import com.mrkumar.foodrecipieapp.viewmodel.MealDetailViewModel
import com.mrkumar.foodrecipieapp.viewmodel.MealDetailViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealDetailMVVM:MealDetailViewModel
    private var mealToSave:Meal?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mealDetailMVVM=ViewModelProviders.of(this)[MealDetailViewModel::class.java]
        val mealDatabase=MealDatabase.getInstance(this)
        val mealVMFactory=MealDetailViewModelFactory(mealDatabase)
        mealDetailMVVM =ViewModelProvider(this,mealVMFactory)[MealDetailViewModel::class.java]

        getMealDataFromFragment()
        setDetailsInViewa()

        loadingCase()
        mealDetailMVVM.getMealDetail(mealId)

        observeMealDetailsLiveData()
        onYoutubeImgClick()
        onFavouriteClick()
    }

    private fun onFavouriteClick() {
        binding.btnSave.setOnClickListener {
            mealToSave?.let { it1 -> mealDetailMVVM.insertMealData(it1) }
            Toast.makeText(this,"Meals Added To Favorites",Toast.LENGTH_SHORT).show()
        }
    }

    private fun onYoutubeImgClick() {
        binding.imgYoutube.setOnClickListener {
            val intent=Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        mealDetailMVVM.observeMealDetailsLiveData().observe(this
        ) { mealData ->
            onResponseCase()
            mealToSave=mealData
            binding.tvCategoryInfo.text = "Category : ${mealData.strCategory}"
            binding.tvAreaInfo.text = "Area : ${mealData.strArea}"
            binding.tvInstructions.text = mealData.strInstructions

            youtubeLink = mealData.strYoutube!!
        }
    }

    private fun setDetailsInViewa() {
        Glide.with(this)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title=mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))
    }

    private fun getMealDataFromFragment() {
        val intent=intent
        mealId= intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName= intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb= intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility=View.VISIBLE
        binding.btnSave.visibility= View.INVISIBLE
        binding.tvInstructions.visibility= View.INVISIBLE
        binding.tvCategoryInfo.visibility= View.INVISIBLE
        binding.tvAreaInfo.visibility= View.INVISIBLE
        binding.imgYoutube.visibility= View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility=View.INVISIBLE
        binding.btnSave.visibility= View.VISIBLE
        binding.tvInstructions.visibility= View.VISIBLE
        binding.tvCategoryInfo.visibility= View.VISIBLE
        binding.tvAreaInfo.visibility= View.VISIBLE
        binding.imgYoutube.visibility= View.VISIBLE
    }
}