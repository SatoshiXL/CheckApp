package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.healthrecorder.databinding.ActivityMainmenuBinding
import com.google.firebase.auth.FirebaseAuth

private lateinit var binding: ActivityMainmenuBinding

class Mainmenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mainmenu)
        supportActionBar?.hide()

        val userID = intent.getStringExtra("user_id")
        val emailID = intent.getStringExtra("email_id")



        binding.LogoutButton.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, Login::class.java))
            finish()
        }

        binding.ClinicVisitButton.setOnClickListener { startActivity(Intent(this, ClinicVisitMain::class.java)) }

    }
}