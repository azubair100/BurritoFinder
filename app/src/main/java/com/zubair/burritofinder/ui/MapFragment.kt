package com.zubair.burritofinder.ui

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


class MapFragment : Fragment(), OnMapReadyCallback{
    override fun onMapReady(googleMap: GoogleMap?) {
        MapsInitializer.initialize(context)
        googleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
        googleMap?.addMarker(MarkerOptions().position(LatLng(latitude!!, longitude!!)).title(name).snippet(street))
        var cameraSize = CameraPosition.builder().target(LatLng(latitude!!, longitude!!)).zoom(16f).bearing(0f).tilt(45f).build()
        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraSize))
    }

    private var name: String? = null
    private var street: String? = null
    private var latitude: Double? = null
    private var longitude: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            name = it.getString("name")
            street = it.getString("street")
            latitude = it.getDouble("latitude")
            longitude = it.getDouble("longitude")
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
        activity?.title = name
        description_bottom_text_view.text = street
    }


    companion object {

        @JvmStatic
        fun newInstance(name: String, street: String, latitude: Double, longitude: Double) =
            MapFragment().apply {
                arguments = Bundle().apply {
                    putString("name", name)
                    putString("street", street)
                    putDouble("latitude", latitude)
                    putDouble("longitude", longitude)
                }
            }
    }
}
