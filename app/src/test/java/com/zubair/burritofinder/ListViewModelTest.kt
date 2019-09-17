package com.zubair.burritofinder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zubair.burritofinder.model.CommonLocation
import com.zubair.burritofinder.model.Geometry
import com.zubair.burritofinder.model.Restaurant
import com.zubair.burritofinder.model.Results
import com.zubair.burritofinder.network.RestaurantApi
import com.zubair.burritofinder.view_model.ListViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class ListViewModelTest {
    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var restaurantApi: RestaurantApi

    @InjectMocks
    lateinit var listViewModel: ListViewModel


    private var testSingle : Single<Results>? = null

    fun setUp(){ MockitoAnnotations.initMocks(this) }

    @Test
    fun getRestaurantsSuccess(){
        val restaurant = Restaurant(
            Geometry(CommonLocation(40.7127753, -74.0059728)),
            "City View Inn",
            "3317 GreenPoint Avenue, Long Island City"
            )

        val restaurantList = arrayListOf(restaurant)
        testSingle = Single.just(Results(restaurantList))

        Mockito.`when`(
            restaurantApi.getRestaurants(
                "restaurant",
                "35.51753, -71.3521",
                "burrito",
                "Azoasdd")).thenReturn(testSingle)

        listViewModel.fetchRestaurants( "restaurant",
            "35.51753, -71.3521",
            "burrito",
            "Azoasdd")

        Assert.assertEquals(1, listViewModel.restaurants.value?.size)
        Assert.assertEquals(false, listViewModel.restaurantLoadError.value)
        Assert.assertEquals(false, listViewModel.loading.value)
    }

    @Before
    fun setUpRxSchedulers(){
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker = ExecutorScheduler.ExecutorWorker(Executor { it.run() })
        }

        RxJavaPlugins.setInitIoSchedulerHandler {scheduler -> immediate}
        RxJavaPlugins.setInitComputationSchedulerHandler {scheduler -> immediate}
        RxJavaPlugins.setInitNewThreadSchedulerHandler {scheduler -> immediate}
        RxJavaPlugins.setInitSingleSchedulerHandler {scheduler -> immediate}
        RxAndroidPlugins.setMainThreadSchedulerHandler {scheduler -> immediate}
    }

}