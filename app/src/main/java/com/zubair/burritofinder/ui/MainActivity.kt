package com.zubair.burritofinder.ui

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.zubair.burritofinder.model.CommonLocation
import com.zubair.burritofinder.model.LocationLiveData
import com.zubair.burritofinder.R
import com.zubair.burritofinder.util.PermissionRequester
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var permissionRequester: PermissionRequester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionRequester = PermissionRequester(WeakReference(this))
    }

    private fun createLocationFragment(commonCommonLocation: CommonLocation?){
        // you would have to pass the commonLocation

        val fragment = BurritoRestaurantListFragment.newInstance(
            commonCommonLocation?.latitude!!, commonCommonLocation!!.longitude)
        supportFragmentManager.beginTransaction().
            add(R.id.main_activity_container, fragment).
            commit()
    }

    override fun onStart() {
        super.onStart()
        if (!permissionRequester.hasPermissions()) {
            permissionRequester.requestPermissions()
        } else {
            getLocation()
        }
    }

    private fun getLocation(){
        val liveData: LiveData<CommonLocation?> =
            LocationLiveData(this)
        liveData.observe(this,
            Observer { commonLocation -> createLocationFragment(commonLocation) })
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "No permission granted", Toast.LENGTH_LONG).show()
                finish()
                return
            }
            else{
                getLocation()
            }
        }
    }

}


