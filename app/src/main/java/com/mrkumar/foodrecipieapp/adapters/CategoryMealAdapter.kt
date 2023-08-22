package com.mrkumar.foodrecipieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrkumar.foodrecipieapp.databinding.MealItemBinding
import com.mrkumar.foodrecipieapp.model.CategoryMeal

class CategoryMealAdapter:RecyclerView.Adapter<CategoryMealAdapter.CategoryViewHolder>(){
    private var mealList=ArrayList<CategoryMeal>()

    fun setMealList(mealList: List<CategoryMeal>){
        this.mealList=mealList as ArrayList<CategoryMeal>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(MealItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(holder.itemView).load(mealList[position].strMealThumb).into(holder.binding.imgMeal)
        holder.binding.tvMealName.text=mealList[position].strMeal
    }

    class CategoryViewHolder(var binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

}