package com.example.feenoxapp.ui

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.feenoxapp.R
import com.example.feenoxapp.ui.collect.CollectFeelingsActivity
import com.example.feenoxapp.ui.moments.MomentsActivity
import com.example.feenoxapp.utils.Constant
import com.example.foodapp.utils.showToast
import com.example.foodapp.utils.startActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkAppPermission()

        btn_collect.setOnClickListener {
            startActivity<CollectFeelingsActivity>(finish = false)
        }

        btn_moments.setOnClickListener {
            startActivity<MomentsActivity>(finish = false)
        }

        tv_logout.setOnClickListener {
            val editor: SharedPreferences.Editor =  Constant.sharedPreferences.edit()
            editor.putBoolean(Constant.IS_LOGGED_KEY, false)
            editor.apply()
            finish()
        }
    }

    private fun checkAppPermission() {
        val storage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (storage != PackageManager.PERMISSION_GRANTED) {
            getAppPermissions()
        }
    }

    private fun getAppPermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                    if (multiplePermissionsReport.areAllPermissionsGranted())
                        showToast("Permissions are granted")
                    if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied)
                        showSettingsDialog()
                }
                override fun onPermissionRationaleShouldBeShown(
                    list: List<PermissionRequest?>?,
                    permissionToken: PermissionToken
                ) {
                    permissionToken.continuePermissionRequest()
                }
            }).withErrorListener {
                showToast("Error occurred! ")
            }.onSameThread().check()
    }

    private fun showSettingsDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Need Permissions")
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
        builder.setPositiveButton("GOTO SETTINGS") { dialog, _ ->
            dialog.cancel()
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivityForResult(intent, 500)
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }
}