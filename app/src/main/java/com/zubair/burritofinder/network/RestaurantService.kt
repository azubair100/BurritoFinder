package com.zubair.burritofinder.network

import com.zubair.burritofinder.model.Restaurant
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {
    @GET("place/nearbysearch/json")
    fun getRestaurants(
        @Query("type") type: String?,
        @Query("location") location: String?,
        @Query("radius") radius: Int,
        @Query("keyword") keyword: String,
        @Query("key") key: String): Single<List<Restaurant>>

}

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?
// location=40.730610,-73.935242&radius=1500&type=restaurant&keyword=burrito&key=AIzaSyAVy6UM0gvzeyYnGfIjR4pmLA8i22QWLTE