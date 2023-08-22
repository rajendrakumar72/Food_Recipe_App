package com.mrkumar.foodrecipieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrkumar.foodrecipieapp.model.CategoryMeal
import com.mrkumar.foodrecipieapp.model.CategoryPopularList
import com.mrkumar.foodrecipieapp.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel:ViewModel() {
    private var mealCategoryLiveData= MutableLiveData<List<CategoryMeal>>()

    fun getMealsByCategory(category:String){
        RetrofitInstance.api.getMealsByCategory(category).enqueue(object :Callback<CategoryPopularList>{
            override fun onResponse(
                call: Call<CategoryPopularList>,
                response: Response<CategoryPopularList>
            ) {
                response.body()?.let { mealCategoryList ->
                    mealCategoryLiveData.postValue(mealCategoryList.meals)
                }
            }

            override fun onFailure(call: Call<CategoryPopularList>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message.toString()}")
            }

        })
    }

    fun observerCategoryLiveData():LiveData<List<CategoryMeal>>{
        return mealCategoryLiveData
    }
}