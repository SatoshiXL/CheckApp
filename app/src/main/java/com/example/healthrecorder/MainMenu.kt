package com.example.healthrecorder

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.databinding.DataBindingUtil
import com.example.healthrecorder.model.User
import com.example.healthrecorder.utils.GlideLoader

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.activity_mainmenu.*
import kotlinx.android.synthetic.main.nav_header.*


class MainMenu : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mainmenu)

        supportActionBar?.hide()


        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        db.collection("users").document(currentUser.uid).get()
            .addOnSuccessListener { documentSnapShot ->
                val user = documentSnapShot.toObject<User>()
                val name = user?.firstName.toString()
                textView5.text = user?.firstName.toString().capitalize()
                textView6.text = user?.lastName.toString().capitalize()
                textView7.text = user?.email.toString()
                val phone = user?.mobile.toString()
                phoneNumber.text = "+63-$phone"
                GlideLoader(this).loadUserPicture(user!!.image, iv_user_image)
            }

        logout_button.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("Yes") { _, _ ->

                FirebaseAuth.getInstance().signOut()

                startActivity(Intent(this, Login::class.java))
                finish()


            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Sign Out")
            builder.setMessage(
                "Are you sure you want to Sign Out?"
            )
            builder.create().show()
        }






        f1.setOnClickListener {
            startActivity(
                Intent(
                    this, ClinicVisitMain::class.java
                )
            )
        }

        f2.setOnClickListener {

            startActivity(Intent(this, IllnessHistoryMain::class.java))

        }

        f3.setOnClickListener {

            startActivity(Intent(this, MedicationMain::class.java))
        }

        f4.setOnClickListener {
            startActivity(Intent(this, AboutUs::class.java))
        }

        f5.setOnClickListener {
            startActivity(
                Intent(
                    this, UserProfile::class.java
                )
            )
        }


        val userID = intent.getStringExtra("user_id")
        val emailID = intent.getStringExtra("email_id")


//        binding.LogoutButton.setOnClickListener {
//            FirebaseAuth.getInstance().signOut()
//
//            startActivity(Intent(this, Login::class.java))
//            finish()
//        }
//
//        binding.ClinicVisitButton.setOnClickListener { startActivity(Intent(this, ClinicVisitMain::class.java)) }
//
//    }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
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