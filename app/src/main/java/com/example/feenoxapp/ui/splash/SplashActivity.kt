package com.example.feenoxapp.ui.splash

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.feenoxapp.R
import com.example.feenoxapp.ui.MainActivity
import com.example.feenoxapp.ui.authentication.UserAuthenticationActivity
import com.example.feenoxapp.ui.moments.MomentsActivity
import com.example.feenoxapp.utils.Constant.Companion.IS_LOGGED_KEY
import com.example.feenoxapp.utils.Constant.Companion.sharedPreferences
import com.example.foodapp.utils.startActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val isLogged = sharedPreferences.getBoolean(IS_LOGGED_KEY, false)
            if(isLogged)
                startActivity<MainActivity>(finish = true)
            else
                startActivity<UserAuthenticationActivity>(finish = true)
        }, 3000)
    }
}