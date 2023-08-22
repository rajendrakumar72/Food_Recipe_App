package com.mrkumar.foodrecipieapp.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mrkumar.foodrecipieapp.model.Category
import com.mrkumar.foodrecipieapp.model.CategoryMeal
import com.mrkumar.foodrecipieapp.model.CategoryPopularList
import com.mrkumar.foodrecipieapp.model.Meal
import com.mrkumar.foodrecipieapp.model.MealCategoryList
import com.mrkumar.foodrecipieapp.model.MealList
import com.mrkumar.foodrecipieapp.api.RetrofitInstance
import com.mrkumar.foodrecipieapp.database.MealDatabase
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val mealDatabase: MealDatabase):ViewModel() {

    private var randomMealLiveData=MutableLiveData<Meal>()
    private var categoryPopularMeal=MutableLiveData<List<CategoryMeal>>()
    private var mealCategoryLiveData=MutableLiveData<List<Category>>()
    private var favoriteMealLiveData=mealDatabase.mealDao().getAllMeals()
    private var bottomSheetLiveData=MutableLiveData<Meal>()
    private var searchMealLiveData=MutableLiveData<List<Meal>>()
    private var saveStateRandomMeal:Meal ?= null
    fun getMealData(){
        saveStateRandomMeal?.let { randomMeal->
            randomMealLiveData.postValue(randomMeal)
            return
        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!=null){
                    val meal: Meal =response.body()!!.meals[0]
                    randomMealLiveData.value=meal
                    saveStateRandomMeal = meal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", "onFailureMealList: ${t.message.toString()}" )
            }

        })
    }

    fun getCategoryMeal(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<MealCategoryList>{
            override fun onResponse(
                call: Call<MealCategoryList>,
                response: Response<MealCategoryList>
            ) {
                response.body()?.let { mealCategoryList ->
                    mealCategoryLiveData.postValue(mealCategoryList.categories)
                }
            }

            override fun onFailure(call: Call<MealCategoryList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun getPopularFood(){
        RetrofitInstance.api.getPopularItems("seafood").enqueue(object :Callback<CategoryPopularList>{
            override fun onResponse(
                call: Call<CategoryPopularList>,
                response: Response<CategoryPopularList>
            ) {
                if(response.body()!=null){
                    categoryPopularMeal.value= response.body()!!.meals
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryPopularList>, t: Throwable) {
                Log.e("TAG", "onFailureCategoryPopularList: ${t.message.toString()}" )
            }

        })
    }

    fun searchMealData(mealQuery:String)=RetrofitInstance.api.searchMealsByName(mealQuery).enqueue(
        object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList=response.body()?.meals
                mealList?.let {
                    searchMealLiveData.postValue(it)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", "onFailureSearchMealData: ${t.message.toString()}" )
            }

        }
    )
    fun getMealById(id:String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val meal = response.body()?.meals?.first()
                meal?.let {meal ->
                   bottomSheetLiveData.postValue(meal)
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.e("TAG", "onFailureGetMealById: ${t.message.toString()}" )
            }

        })
    }
    fun deleteMealData(meal: Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().delete(meal)
        }
    }

    fun insertMealData(meal:Meal){
        viewModelScope.launch {
            mealDatabase.mealDao().update(meal)
        }
    }

    fun observeMealLiveDat():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularMeaLiveData():LiveData<List<CategoryMeal>>{
        return categoryPopularMeal
    }

    fun observeMealCategoryList():LiveData<List<Category>>{
        return mealCategoryLiveData
    }

    fun observeFavoriteMealLiveData():LiveData<List<Meal>>{
        return favoriteMealLiveData
    }

    fun observeBottomSheetLiveData():LiveData<Meal>{
        return bottomSheetLiveData
    }

    fun observeSearchLiveData():LiveData<List<Meal>>{
        return searchMealLiveData
    }
}