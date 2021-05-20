package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import com.example.healthrecorder.databinding.ActivityLoginBinding
import com.example.healthrecorder.firestore.FirestoreClass
import com.example.healthrecorder.model.ClinicVisit
import com.example.healthrecorder.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.muddzdev.styleabletoast.StyleableToast

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

        binding.forgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPassword::class.java)
            startActivity(intent)
        }

        binding.SignInButton.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.loginEmail.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(binding.loginPassword.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(this, "Please Enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = binding.loginEmail.text.toString().trim() { it <= ' ' }
                    val password: String =
                        binding.loginPassword.text.toString().trim() { it <= ' ' }




                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {

                                if (FirebaseAuth.getInstance().currentUser.isEmailVerified) {
                                    val mFireStore = FirebaseFirestore.getInstance()
                                    val currentUser = FirebaseAuth.getInstance().currentUser
                                    val docRef =
                                        mFireStore.collection("users").document(currentUser.uid)
                                    docRef.get().addOnSuccessListener { documentSnapShot ->
                                        val user = documentSnapShot.toObject<User>()

                                        if (user!!.profileCompleted == 0) {
                                            FirestoreClass().getUserDetails(this@Login)
                                            val firebaseUser: FirebaseUser = task!!.result!!.user!!
                                            val intent = Intent(this, UserProfile::class.java)
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            intent.putExtra("user_id", firebaseUser.uid)
                                            intent.putExtra("email_id", email)
                                            startActivity(intent)
                                            finish()


                                        } else {
                                            FirestoreClass().getUserDetails(this@Login)

                                            val firebaseUser: FirebaseUser = task!!.result!!.user!!
                                            val intent = Intent(this, MainMenu::class.java)
                                            intent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                            intent.putExtra("user_id", firebaseUser.uid)
                                            intent.putExtra("email_id", email)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                                } else {
                                    StyleableToast.makeText(
                                        this@Login,
                                        "Account not verified please check your Email address",
                                        R.style.exampleToast
                                    ).show()
                                }

                            }
                        }

                }


            }
        }


    }

    fun userLoggedInSuccess(user: User) {

        Log.i("First Name", user.firstName)
        Log.i("Last Name", user.lastName)
        Log.i("Email", user.email)
        finish()
    }
}




