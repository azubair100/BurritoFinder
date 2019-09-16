package com.zubair.burritofinder.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zubair.burritofinder.model.Restaurant
import com.zubair.burritofinder.network.RestaurantApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class ListViewModel : ViewModel(){
    val restaurants = MutableLiveData<List<Restaurant>>()
    val restaurantLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()
    private val restaurantApi: RestaurantApi = RestaurantApi()

    fun refresh(){ fetchRestaurants() }

    private fun fetchRestaurants(){
        loading.value = true
        disposable.add(
            restaurantApi.getRestaurants()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Restaurant>>() {
                    override fun onSuccess(value: List<Restaurant>) {
                        restaurants.value = value
                        Log.d("count", "count is in ListViewModel " + value.size)
                        restaurantLoadError.value = false
                        loading.value = false
                    }
                    override fun onError(e: Throwable) {
                        restaurantLoadError.value = true
                        loading.value = false
                    }

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}