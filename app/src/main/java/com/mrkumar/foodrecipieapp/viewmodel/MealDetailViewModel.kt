package com.mrkumar.foodrecipieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mrkumar.foodrecipieapp.pojo.Meal
import com.mrkumar.foodrecipieapp.pojo.MealList
import com.mrkumar.foodrecipieapp.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailViewModel:ViewModel() {

    private var mealDetailLiveData=MutableLiveData<Meal>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if (response.body()!=null){
                    val mealDetails:Meal=response.body()!!.meals[0]
                    mealDetailLiveData.value=mealDetails
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", "onFailure: ${t.message.toString()}" )
            }

        })
    }

    fun observeMealDetailsLiveData():LiveData<Meal>{
        return mealDetailLiveData
    }
}