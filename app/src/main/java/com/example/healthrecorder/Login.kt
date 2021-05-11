package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.databinding.DataBindingUtil
import com.example.healthrecorder.databinding.ActivityLoginBinding

private lateinit var binding: ActivityLoginBinding

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.SignupNow.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }


    }
}