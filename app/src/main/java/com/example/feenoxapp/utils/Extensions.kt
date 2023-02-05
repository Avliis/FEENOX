package com.example.foodapp.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.feenoxapp.utils.Constant.Companion.APP_CXT
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun showToast(message: Any) {
    Toast.makeText(APP_CXT!!, message.toString(), Toast.LENGTH_LONG).show()
}

fun showLog(message: Any) {
    Log.d("Shahbaz", message.toString())
}

inline fun <reified T> Activity.startActivity(finish: Boolean = true) {
    Intent(this, T::class.java).apply {
        startActivity(this)
        if (finish) {
            finish()
        }
    }
}