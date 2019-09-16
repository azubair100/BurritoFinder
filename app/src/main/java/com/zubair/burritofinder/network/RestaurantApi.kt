package com.zubair.burritofinder.network

import com.zubair.burritofinder.model.Restaurant
import com.zubair.burritofinder.model.Results
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestaurantApi {
    private val BASE_URL = "https://maps.googleapis.com/maps/api/"
    private val service : RestaurantService

    init {
        service = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(provideOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(RestaurantService::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient {
        val interceptor =
            HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(300, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(30, TimeUnit.SECONDS)
        okHttpClientBuilder.addInterceptor(interceptor)
        return okHttpClientBuilder.build()
    }

    fun getRestaurants(
        type : String,
        location : String,
        keyword : String,
        key : String
    ) : Single<Results>
            = service.getRestaurants(location, 50000, type, keyword, key)
}


//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=burrito&key=AIzaSyBb_KeTZvx-Qv3oP3O1RwPHJqoECXcFo7g