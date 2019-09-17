package com.zubair.burritofinder.di

import com.zubair.burritofinder.network.RestaurantApi
import com.zubair.burritofinder.view_model.ListViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject(api : RestaurantApi)
    fun inject(viewModel: ListViewModel)
}