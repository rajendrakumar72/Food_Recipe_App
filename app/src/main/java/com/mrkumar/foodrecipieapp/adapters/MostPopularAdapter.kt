package com.mrkumar.foodrecipieapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrkumar.foodrecipieapp.databinding.PoupularItemListBinding
import com.mrkumar.foodrecipieapp.model.CategoryMeal

class MostPopularAdapter():RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>(){
    private var mealList=ArrayList<CategoryMeal>()
    lateinit var onItemClick:((CategoryMeal) -> Unit)

    var onLogItemClick:((CategoryMeal)->Unit)?=null

    fun setMeals(mealList:ArrayList<CategoryMeal>){
        this.mealList=mealList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PoupularItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealList[position].strMealThumb).into(holder.binding.imgPopularMeal)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealList[position])
        }

        holder.itemView.setOnLongClickListener {
            onLogItemClick?.invoke(mealList[position])
            true
        }

    }


    class PopularMealViewHolder(var binding:PoupularItemListBinding):RecyclerView.ViewHolder(binding.root){

    }
}