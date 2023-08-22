package com.mrkumar.foodrecipieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrkumar.foodrecipieapp.databinding.CategoryItemBinding
import com.mrkumar.foodrecipieapp.model.Category

class MealCategoriesAdapter(): RecyclerView.Adapter<MealCategoriesAdapter.MealCategoriesViewHolder>() {

    private var categoryList=ArrayList<Category>()
    var onItemClick: ((Category) -> Unit)?= null

    fun setCategoryList(categoryList:List<Category>){
        this.categoryList=categoryList as ArrayList<Category>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealCategoriesViewHolder {
        return MealCategoriesViewHolder(CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: MealCategoriesViewHolder, position: Int) {
        Glide.with(holder.itemView).load(categoryList[position].strCategoryThumb).into(holder.binding.imgCategory)
        holder.binding.tvCategoryName.text=categoryList[position].strCategory
        holder.itemView.setOnClickListener {
            onItemClick!!.invoke(categoryList[position])

        }
    }


    class MealCategoriesViewHolder(var binding: CategoryItemBinding):RecyclerView.ViewHolder(binding.root){

    }


}