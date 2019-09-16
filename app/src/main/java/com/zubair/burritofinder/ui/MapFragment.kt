package com.zubair.burritofinder.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.zubair.burritofinder.R
import kotlinx.android.synthetic.main.fragment_map.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MapFragment : Fragment(), OnMapReadyCallback{
    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(context)
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap?.addMarker(MarkerOptions().position(LatLng(40.730610,-73.935242)).title("Test NYC").snippet("Testing with hardcoded latlng"))
        var cameraSize = CameraPosition.builder().target(LatLng(40.730610,-73.935242)).zoom(16f).bearing(0f).tilt(45f).build()
        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraSize))
    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(map_view != null){
            map_view.onCreate(null)
            map_view.onResume()
            map_view.getMapAsync(this)
        }
        activity?.title = "Name Change"
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
