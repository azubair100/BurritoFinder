package com.zubair.burritofinder.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zubair.burritofinder.R
import com.zubair.burritofinder.model.Restaurant
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantListAdapter(var restuarants: ArrayList<Restaurant>) : RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {

    fun refreshRestaurantList(newRestaurants: List<Restaurant>){
        restuarants.clear()
        restuarants.addAll(newRestaurants)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parentView: ViewGroup, p1: Int): RestaurantViewHolder  = RestaurantViewHolder(
        LayoutInflater.from(parentView.context).inflate(R.layout.item_restaurant, parentView, false)
    )

    override fun getItemCount() = restuarants.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restuarants[position])
    }

    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view){

        private val restaurantName = view.restaurant_row_name
        private val restaurantDescription = view.restaurant_row_description

        fun bind(restaurant: Restaurant){
            restaurantName.text = restaurant.name ?: ""
            restaurantDescription.text = restaurant.vicinity

        }
    }
}