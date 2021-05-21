package com.example.healthrecorder.firestore

import android.app.Activity
import android.net.Uri
import android.util.Log
import com.example.healthrecorder.Login
import com.example.healthrecorder.R
import com.example.healthrecorder.Register
import com.example.healthrecorder.UserProfile
import com.example.healthrecorder.model.ClinicVisit
import com.example.healthrecorder.model.User
import com.example.healthrecorder.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.muddzdev.styleabletoast.StyleableToast

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()
    private val mCurrentUser = FirebaseAuth.getInstance().currentUser.uid


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

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {

        mFireStore.collection("users").document(mCurrentUser).update(userHashMap)
            .addOnSuccessListener {}
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?) {

        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtension(
                activity,
                imageFileURI
            )
        )

        sRef.putFile(imageFileURI!!).addOnSuccessListener { takeSnapshot ->
            Log.e(
                "Firebase image URL",
                takeSnapshot.metadata!!.reference!!.downloadUrl.toString()
            )

            takeSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                Log.e("Downloadable Image URL", uri.toString())
                when(activity){
                    is UserProfile -> {
                        activity.imageUploadSuccess(uri.toString())
                    }
                }
            }

        }.addOnFailureListener{exception ->
            when(activity){
                is UserProfile ->{
                    StyleableToast.makeText(activity,"Error", R.style.exampleToast).show()
                }
            }
            Log.e(activity.javaClass.simpleName,exception.message,exception)
    }

}


}




