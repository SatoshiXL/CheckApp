package com.example.healthrecorder.firestore

import android.app.Activity
import com.example.healthrecorder.Login
import com.example.healthrecorder.Register
import com.example.healthrecorder.model.ClinicVisit
import com.example.healthrecorder.model.User
import com.example.healthrecorder.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()


    fun registerUser(activity: Register, userInfo: User) {


        mFireStore.collection(Constants.USERS).document(userInfo.id)
            .set(userInfo, SetOptions.merge())
    }


    fun getCurrentUserID(): String {
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
        mFireStore.collection(Constants.USERS).document(getCurrentUserID()).get()
            .addOnSuccessListener { document ->
                val user = document.toObject(User::class.java)!!

                when (activity) {
                    is Login -> {
                        activity.userLoggedInSuccess(user)
                    }
                }
            }
    }


}


