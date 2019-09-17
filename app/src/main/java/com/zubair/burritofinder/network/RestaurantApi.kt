package com.zubair.burritofinder.network

import com.zubair.burritofinder.di.DaggerApiComponent
import com.zubair.burritofinder.model.Results
import io.reactivex.Single
import javax.inject.Inject

class RestaurantApi {
    @Inject
    lateinit var service : RestaurantService

    init { DaggerApiComponent.create().inject(this) }


    fun getRestaurants(
        type : String,
        location : String,
        keyword : String,
        key : String
    ) : Single<Results>
            = service.getRestaurants(location, 5000, type, keyword, key)
}
