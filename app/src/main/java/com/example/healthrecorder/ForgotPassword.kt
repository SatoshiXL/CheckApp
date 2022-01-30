package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.activity_login.*

class ForgotPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        hideSystemUI()


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

    private fun hideSystemUI() {
// Enables regular immersive mode.
// For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
// Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

}