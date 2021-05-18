package com.example.healthrecorder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.healthrecorder.databinding.ActivityRegisterBinding
import com.example.healthrecorder.firestore.FirestoreClass
import com.example.healthrecorder.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private lateinit var binding: ActivityRegisterBinding

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        supportActionBar?.hide()


        binding.SignUpButton.setOnClickListener {
            when {
                TextUtils.isEmpty(binding.registerEmail.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(this, "Please Enter email", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(binding.registerPassword.text.toString().trim() { it <= ' ' }) -> {
                    Toast.makeText(this, "Please Enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email: String = binding.registerEmail.text.toString().trim() { it <= ' ' }
                    val password: String = binding.registerPassword.text.toString().trim() { it <= ' ' }

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {

                                    val firebaseUser: FirebaseUser = task!!.result!!.user!!

                                    val user = User(
                                        firebaseUser.uid,
                                        binding.registerFirstName.text.toString().trim() { it <= ' '},
                                        binding.registerLastName.text.toString().trim() { it <= ' '},
                                        binding.registerEmail.text.toString().trim() { it <= ' '},

                                    )

                                    FirestoreClass().registerUser(this, user)

                                    Toast.makeText(this, "You Registered Successfully", Toast.LENGTH_SHORT).show()


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