package com.mrkumar.foodrecipieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MealInformation")
data class MealDBModel(
                        @PrimaryKey
                        val mealId: Int,
                        val mealName: String,
                        val mealCountry: String,
                        val mealCategory:String,
                        val mealInstruction:String,
                        val mealThumb:String,
                        val mealYoutubeLink:String)
