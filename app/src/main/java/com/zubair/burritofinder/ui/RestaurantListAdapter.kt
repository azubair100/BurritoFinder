package com.zubair.burritofinder.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.zubair.burritofinder.R
import com.zubair.burritofinder.model.Restaurant
import kotlinx.android.synthetic.main.item_restaurant.view.*

class RestaurantListAdapter(var restaurants: ArrayList<Restaurant>, val clickListener: (Restaurant) -> Unit) : RecyclerView.Adapter<RestaurantListAdapter.RestaurantViewHolder>() {

    fun refreshRestaurantList(newRestaurants: List<Restaurant>){
        restaurants.clear()
        restaurants.addAll(newRestaurants)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parentView: ViewGroup, p1: Int): RestaurantViewHolder  = RestaurantViewHolder(
        LayoutInflater.from(parentView.context).inflate(R.layout.item_restaurant, parentView, false)
    )

    override fun getItemCount() = restaurants.size

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position], clickListener)
    }

    class RestaurantViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(restaurant: Restaurant, clickListener: (Restaurant) -> Unit){
            itemView.restaurant_row_name.text = restaurant.name ?: ""
            itemView.restaurant_row_description.text = restaurant.vicinity
            itemView.setOnClickListener { clickListener(restaurant)}
        }
    }
}