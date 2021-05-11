package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)


        supportActionBar?.hide()
        Handler().postDelayed({
            val Intent = Intent(this@SplashScreen, Login::class.java)
            startActivity(Intent)
            finish()
        }, 3000)


    }
}
