package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login.*

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        forgotpassword_submit.setOnClickListener {

            if ((forgotPassword_text.text!!.isEmpty())) {
                StyleableToast.makeText(this, "Please enter your Email ID", R.style.exampleToast)
                    .show()
            }

            else {
                val fb = FirebaseAuth.getInstance()
                fb.sendPasswordResetEmail(forgotPassword_text.text.toString())
                    .addOnSuccessListener {
                        StyleableToast.makeText(
                            this,
                            "Password Reset Sent to your email",
                            R.style.exampleToast
                        ).show()
                        val intent = Intent(this, Login::class.java)
                        startActivity(intent)
                    }.addOnFailureListener {
                        StyleableToast.makeText(this, "Invalid Email", R.style.exampleToast).show()
                    }
            }
        }
    }

}