package com.mrkumar.foodrecipieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.mrkumar.foodrecipieapp.pojo.Meal
import com.mrkumar.foodrecipieapp.pojo.MealList
import com.mrkumar.foodrecipieapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel:ViewModel() {

    private var randomMealLiveData=MutableLiveData<Meal>()

    fun getMealData(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val meal: Meal =response.body()!!.meals[0]
                    randomMealLiveData.value=meal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message.toString()}" )
            }

        })
    }

    fun observeMealLiveDat():LiveData<Meal>{
        return randomMealLiveData
    }
}