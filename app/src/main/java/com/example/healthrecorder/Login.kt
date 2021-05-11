package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthrecorder.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

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
                    val password: String = binding.loginPassword.text.toString().trim() { it <= ' ' }

                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    val firebaseUser: FirebaseUser = task!!.result!!.user!!
                                    val intent = Intent(this, Mainmenu::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    intent.putExtra("user_id", firebaseUser.uid)
                                    intent.putExtra("email_id", email)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                                }


                            }
                }

            }


        }
    }

}



