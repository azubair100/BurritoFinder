package com.zubair.burritofinder.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.zubair.burritofinder.R
import com.zubair.burritofinder.model.CommonLocation
import com.zubair.burritofinder.model.LocationLiveData
import com.zubair.burritofinder.view_model.ListViewModel
import kotlinx.android.synthetic.main.fragment_burrito_restaurant_list.*

class BurritoRestaurantListFragment : Fragment() {
    lateinit var viewModel: ListViewModel
    private val restaurantAdapter = RestaurantListAdapter(arrayListOf())

    private var latitude: Double? = null
    private var longitude: Double? = null

    companion object {
        fun newInstance(latitude: Double, longitude: Double): BurritoRestaurantListFragment {
            val fragment = BurritoRestaurantListFragment()
            val args = Bundle()
            args.putDouble("latitude", latitude)
            args.putDouble("longitude", longitude)
            fragment.arguments = args
            return fragment
        }
    }

    private fun observeViewModel(){
        viewModel.restaurants?.observe(this, Observer { restaurants ->
            //if countries is not null, it will be passed in this block as it
            restaurants?.let {
                restaurant_list.visibility = View.VISIBLE
                restaurantAdapter.refreshRestaurantList(it)
            }
        })

        viewModel.restaurantLoadError?.observe(this, Observer { isError ->
            isError?.let { list_error_text.visibility = if(it) View.VISIBLE else View.GONE }
        })

        viewModel.loading?.observe(this, Observer { isLoading ->
            isLoading?.let { list_progress_bar.visibility = if(it) View.VISIBLE else View.GONE
                if (it){
                    list_error_text.visibility = View.GONE
                    restaurant_list.visibility = View.GONE
                }
            }
        })
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble("latitude")
            longitude = it.getDouble("longitude")
        }

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)
        viewModel.fetchRestaurants("restaurant",
            latitude.toString() +"," + longitude,
            "burrito",
            getString(R.string.google_places_api_key)
        )
        //pass commonLocation here
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("count", "count is in RestaurantListFragment " + restaurantAdapter.restuarants.size)

        restaurant_list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = restaurantAdapter
        }
        observeViewModel()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_burrito_restaurant_list, container, false)



    override fun onDetach() {
        super.onDetach()
    }
}
