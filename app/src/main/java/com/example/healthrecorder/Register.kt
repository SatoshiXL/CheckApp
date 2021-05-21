package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthrecorder.databinding.ActivityRegisterBinding
import com.example.healthrecorder.firestore.FirestoreClass
import com.example.healthrecorder.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.activity_register.*

private lateinit var binding: ActivityRegisterBinding

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        supportActionBar?.hide()




        binding.SignUpButton.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.registerEmail.text.toString().trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(this, "Please Enter Email", R.style.exampleToast).show()
                }

                TextUtils.isEmpty(
                        binding.registerPassword.text.toString().trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(this, "Please Enter Password", R.style.exampleToast)
                            .show()
                }

                TextUtils.isEmpty(
                        binding.confirmRegisterPassword.text.toString().trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(
                            this,
                            "Please Enter Confirm Password",
                            R.style.exampleToast
                    ).show()
                }
                else -> {

                    if (confirmPass(
                                    binding.registerPassword.text.toString(),
                                    binding.confirmRegisterPassword.text.toString()
                            )
                    ) {
                        val email: String =
                                binding.registerEmail.text.toString().trim() { it <= ' ' }
                        val password: String =
                                binding.registerPassword.text.toString().trim() { it <= ' ' }


                        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {

                                        val use = FirebaseAuth.getInstance().currentUser
                                        Log.i("message", "$use")
                                        use.sendEmailVerification()
                                                .addOnSuccessListener { Log.i("sent", "sent") }
                                                .addOnFailureListener {
                                                    Log.i("failed", "fail")

                                                }


                                        val firebaseUser: FirebaseUser = task!!.result!!.user!!

                                        val user = User(
                                                firebaseUser.uid,"","",
                                                binding.registerEmail.text.toString().trim() { it <= ' ' }
                                        )

                                        FirestoreClass().registerUser(this, user)

                                        StyleableToast.makeText(
                                                this,
                                                "You've Registered Successfully",
                                                R.style.exampleToast
                                        ).show()


                                        val intent = Intent(this, Login::class.java)
                                        intent.flags =
                                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        intent.putExtra("user_id", firebaseUser.uid)
                                        intent.putExtra("email_id", email)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(
                                                this,
                                                task.exception!!.message.toString(),
                                                Toast.LENGTH_SHORT
                                        ).show()
                                    }

                                }


                    } else {
                        StyleableToast.makeText(this, "Please make sure your password match", R.style.exampleToast).show()
                    }


                }

            }


        }


    }


    private fun confirmPass(password: String, password1: String): Boolean {
        return password == password1
    }

}

