package com.zubair.burritofinder.model

import android.Manifest.permission
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.lifecycle.LiveData
import com.google.android.gms.location.*
import io.reactivex.annotations.NonNull

class LocationLiveData(private val context: Context) :
    LiveData<CommonLocation?>() {
    private var fusedLocationProviderClient: FusedLocationProviderClient? =
        null

    protected override fun onActive() {
        super.onActive()
        if (ActivityCompat.checkSelfPermission(
                context,
                permission.ACCESS_FINE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                context,
                permission.ACCESS_COARSE_LOCATION
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val locationProviderClient =
            getFusedLocationProviderClient()
        val locationRequest: LocationRequest? =
            LocationRequest.create()
        val looper: Looper? = Looper.myLooper()
        locationProviderClient!!.requestLocationUpdates(locationRequest, locationCallback, looper)
    }

    @NonNull
    private fun getFusedLocationProviderClient(): FusedLocationProviderClient? {
        if (fusedLocationProviderClient == null) {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(
                    context
                )
        }
        return fusedLocationProviderClient
    }

    protected override fun onInactive() {
        if (fusedLocationProviderClient != null) {
            fusedLocationProviderClient!!.removeLocationUpdates(locationCallback)
        }
    }

    private val locationCallback: LocationCallback =
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val newCommonLocation: Location? = locationResult.lastLocation
                val latitude = newCommonLocation!!.latitude
                val longitude = newCommonLocation!!.longitude
                val location = CommonLocation(latitude, longitude)
                value = location
            }
        }

}


/*public class LocationLiveData extends LiveData<CommonLocation> {
    private static final double LATITUDE = 51.649836;
    private static final double LONGITUDE = -0.401486;
    private static final float ACCURACY = 5f;
    private static final CommonLocation LOCATION = new CommonLocation(LATITUDE, LONGITUDE, ACCURACY);

    public LocationLiveData(Context context) {
        //NO-OP
    }

    @Override
    protected void onActive() {
        super.onActive();
        setValue(LOCATION);
    }
}*/