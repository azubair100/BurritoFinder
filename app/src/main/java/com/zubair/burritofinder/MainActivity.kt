package com.zubair.burritofinder

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var permissionRequester: PermissionRequester

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        permissionRequester = PermissionRequester(WeakReference(this))
    }

    private fun createLocationFragment(commonLocation: CommonLocation?){
        Toast.makeText(this, "Permission Granted " + commonLocation?.latitude + " " + commonLocation?.longitude, Toast.LENGTH_LONG).show()

    }

    override fun onStart() {
        super.onStart()
        if (!permissionRequester.hasPermissions()) {
            permissionRequester.requestPermissions()
        } else {
            val liveData: LiveData<CommonLocation?> = LocationLiveData(this)
            liveData.observe(this,
                Observer<CommonLocation?> { commonLocation -> createLocationFragment(commonLocation) })
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "No permission granted", Toast.LENGTH_LONG).show()
                finish()
                return
            }
        }
        createLocationFragment(CommonLocation(4.5, 5.4, 4.5f))
    }

}
