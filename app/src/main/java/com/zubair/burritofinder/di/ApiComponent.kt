package com.zubair.burritofinder.di

import com.zubair.burritofinder.network.RestaurantApi
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(api : RestaurantApi)
}