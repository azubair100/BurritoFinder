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
        @Query("key") key: String): Single<List<Restaurant>>

}