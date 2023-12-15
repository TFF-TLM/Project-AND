package be.technifuture.tff.utils.permission

import be.technifuture.tff.utils.sharedPref.SharedPrefManager
import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

class PermissionsCheckerManager(private val activity: Activity) {
    private val sharedPrefManager = SharedPrefManager.instance

    private fun permissionsNotGranted(permissions: Array<String>): Boolean {
        var isNotGranted = false
        permissions.forEach { permission ->
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                isNotGranted = true
            }
        }
        return isNotGranted
    }

    private fun shouldShowRequestPermission(permissions: Array<String>): Boolean {
        var should = false
        permissions.forEach { permission ->
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    permission
                )
            ) {
                should = true
            }
        }
        return should
    }

    fun checkPermissions(requestCode: Int, permissions: Array<String>, handler: () -> Unit) {
        val firstTimeOpening = sharedPrefManager.sharedPref.getBoolean(
            SharedPrefManager.KeyPref.FIRST_TIME_OPENING.value,
            true
        )

        if (permissionsNotGranted(permissions)) {

            if (shouldShowRequestPermission(permissions) || firstTimeOpening) {

                sharedPrefManager.editor.putBoolean(
                    SharedPrefManager.KeyPref.FIRST_TIME_OPENING.value,
                    false
                )
                sharedPrefManager.editor.apply()

                ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    requestCode
                )
            }

            return
        }

        handler()
    }

    fun checkPermissionsLocation(requestCode: Int, handler: () -> Unit) {
        checkPermissions(
            requestCode,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            handler
        )
    }

}