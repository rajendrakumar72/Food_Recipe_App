package com.mrkumar.foodrecipieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkumar.foodrecipieapp.model.Meal
import com.mrkumar.foodrecipieapp.model.MealList
import com.mrkumar.foodrecipieapp.api.RetrofitInstance
import com.mrkumar.foodrecipieapp.database.MealDatabase
import com.mrkumar.foodrecipieapp.model.MealDBModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealDetailViewModel(private val mealDB:MealDatabase):ViewModel() {

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

    fun insertMealData(meal:Meal){
        viewModelScope.launch {
            mealDB.mealDao().update(meal)
        }
    }


}