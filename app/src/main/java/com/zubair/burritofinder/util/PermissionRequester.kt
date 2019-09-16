package com.zubair.burritofinder.util

import android.Manifest.permission
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import java.lang.ref.WeakReference

class PermissionRequester(private val activityWeakReference: WeakReference<Activity>?) {

    private val PERMISSIONS = arrayOf(
        permission.ACCESS_COARSE_LOCATION,
        permission.ACCESS_FINE_LOCATION
    )




    internal fun hasPermissions(): Boolean {
        val activity = activityWeakReference!!.get()
        if (activity != null) {
            for (permission in PERMISSIONS) {
                if (ActivityCompat.checkSelfPermission(activity, permission) !== PackageManager.PERMISSION_GRANTED) return false
            }
            return true
        }
        return false
    }

    internal fun requestPermissions() {
        val activity = activityWeakReference!!.get()
        if (activity != null) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, 0)
        }
    }
}