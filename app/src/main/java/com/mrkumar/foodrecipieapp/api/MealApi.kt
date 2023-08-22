package com.mrkumar.foodrecipieapp.api

import com.mrkumar.foodrecipieapp.model.CategoryPopularList
import com.mrkumar.foodrecipieapp.model.MealCategoryList
import com.mrkumar.foodrecipieapp.model.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {
    @GET("random.php")
    fun getRandomMeal():Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i")id:String):Call<MealList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c")categoryName:String):Call<CategoryPopularList>

    @GET("categories.php")
    fun getCategories():Call<MealCategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c")categoryName:String):Call<CategoryPopularList>

    @GET("search.php")
    fun searchMealsByName(@Query("s")mealName:String):Call<MealList>

}