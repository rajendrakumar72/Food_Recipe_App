package com.mrkumar.foodrecipieapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

import com.mrkumar.foodrecipieapp.R
import com.mrkumar.foodrecipieapp.adapters.FavoritesMealAdapter
import com.mrkumar.foodrecipieapp.databinding.FragmentFavoritesBinding
import com.mrkumar.foodrecipieapp.ui.activity.MainActivity
import com.mrkumar.foodrecipieapp.viewmodel.HomeViewModel


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var favoriteAdapter:FavoritesMealAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentFavoritesBinding.inflate(inflater)

        viewModel=(activity as MainActivity).viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecView()
        observeFavoritesMeals()

        val itemTouchHelper =object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoriteAdapter.differ.currentList[position]
                viewModel.deleteMealData(deletedMeal)

                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo",View.OnClickListener{
                        viewModel.insertMealData(deletedMeal)

                    }
                ) .show()
            }

        }
        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.favRecView)
    }

    private fun setupRecView() {
        favoriteAdapter= FavoritesMealAdapter()
        binding.favRecView.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoriteAdapter
        }
    }

    private fun observeFavoritesMeals() {
        viewModel.observeFavoriteMealLiveData().observe(requireActivity()) { meals ->
            favoriteAdapter.differ.submitList(meals)
        }
    }
}