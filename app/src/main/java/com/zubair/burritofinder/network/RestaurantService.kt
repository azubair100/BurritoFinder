package com.zubair.burritofinder.network

import com.zubair.burritofinder.model.Results
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RestaurantService {
    @GET("place/nearbysearch/json")
    fun getRestaurants(
        @Query("location") location: String?,
        @Query("radius") radius: Int,
        @Query("type") type: String?,
        @Query("keyword") keyword: String,
        @Query("key") key: String): Single<Results>

}