package com.zubair.burritofinder.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zubair.burritofinder.di.DaggerApiComponent
import com.zubair.burritofinder.model.Restaurant
import com.zubair.burritofinder.model.Results
import com.zubair.burritofinder.network.RestaurantApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel : ViewModel(){
    val restaurants = MutableLiveData<List<Restaurant>>()
    val restaurantLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    private val disposable = CompositeDisposable()

    @Inject
    lateinit var restaurantApi: RestaurantApi

    init { DaggerApiComponent.create().inject(this) }

    fun fetchRestaurants(type : String, location : String, keyword : String, key : String){
        loading.value = true
        disposable.add(
            restaurantApi.getRestaurants(type, location, keyword, key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver <Results>() {
                    override fun onSuccess(value: Results) {
                        restaurants.value = value.results
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