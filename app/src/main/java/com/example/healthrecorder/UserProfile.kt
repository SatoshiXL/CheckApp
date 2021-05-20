package com.example.healthrecorder

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.healthrecorder.firestore.FirestoreClass
import com.example.healthrecorder.fragment.add.toEditable
import com.example.healthrecorder.model.ClinicVisit
import com.example.healthrecorder.model.User
import com.example.healthrecorder.utils.Constants
import com.example.healthrecorder.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.activity_mainmenu.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.activity_user_profile.view.*
import java.io.IOException


class UserProfile : AppCompatActivity() {

    private var mSelectedImageUri: Uri? = null
    private var mUserProfileImageURL: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)


        val user = FirebaseAuth.getInstance().uid
        val db = FirebaseFirestore.getInstance().collection("users").document("$user")
        db.get().addOnSuccessListener { documentSnapShot ->
            val currentUserInfo = documentSnapShot.toObject<User>()
            profile_email_id.isEnabled = false
            profile_email_id.setText(currentUserInfo?.email.toString())
            profile_first_name.setText(currentUserInfo?.firstName.toString())
            profile_last_name.setText(currentUserInfo?.lastName.toString())
            profile_phone_number.setText(currentUserInfo?.mobile.toString())
            GlideLoader(this).loadUserPicture(currentUserInfo!!.image, user_image_prof)

        }


        user_image_frame.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
//                StyleableToast.makeText(this, "You already have the storage permission", R.style.exampleToast).show()
                Constants.showImageChooser(this)
            } else {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.READ_STORAGE_PERMISSION_CODE
                )
            }
        }

        submit_button_profile.setOnClickListener {


            if (validateUserProfileDetails()) {
                if (mSelectedImageUri != null)
                    FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageUri)
                else {
                    updateUserProfileDetails()

                }
            }



        }
    }

    private fun updateUserProfileDetails() {
        val userHashMap = HashMap<String, Any>()
        if (mUserProfileImageURL.isNotEmpty()) {
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
        }
        val mobileNumber = profile_phone_number.text.toString().trim() { it <= ' ' }
        if (mobileNumber.isNotEmpty()) {
            userHashMap["mobile"] = mobileNumber.toString().trim() {it <= ' '}
        }
        val firstName = profile_first_name.text.toString().trim() { it <= ' ' }
        if(firstName.isNotEmpty()){
            userHashMap["firstName"] = firstName
        }
        val lastName = profile_last_name.text.toString().trim() { it <= ' ' }
        if(lastName.isNotEmpty()){
            userHashMap["lastName"] = lastName
        }

        userHashMap[Constants.COMPLETE_PROFILE] = 1

        FirestoreClass().updateUserProfileData(this, userHashMap)

        val intent = Intent(this, MainMenu::class.java)
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intent)


//                StyleableToast.makeText(this,"Your details are valid", R.style.exampleToast).show()

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                StyleableToast.makeText(this, "The Storage permission is granted", R.style.exampleToast).show()
                Constants.showImageChooser(this)
            } else {
                StyleableToast.makeText(
                    this,
                    "Read Storage Permission Denied",
                    R.style.exampleToast
                ).show()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.PICK_IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        mSelectedImageUri = data.data!!

//                        user_image.setImageURI(Uri.parse(selectedImageUri.toString()))
                        GlideLoader(this).loadUserPicture(mSelectedImageUri!!, user_image_prof)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        StyleableToast.makeText(
                            this,
                            "Image selection failed",
                            R.style.exampleToast
                        ).show()

                    }
                }
            }
        }
    }

    private fun validateUserProfileDetails(): Boolean {
        return when {
            TextUtils.isEmpty(profile_phone_number.text.toString().trim { it <= ' ' }) -> {
                StyleableToast.makeText(this, "Please enter phone number", R.style.exampleToast)
                    .show()

                false
            }
            TextUtils.isEmpty(profile_first_name.text.toString().trim { it <= ' ' }) -> {
                StyleableToast.makeText(this, "Please enter phone number", R.style.exampleToast)
                        .show()

                false
            }
            TextUtils.isEmpty(profile_last_name.text.toString().trim { it <= ' ' }) -> {
                StyleableToast.makeText(this, "Please enter phone number", R.style.exampleToast)
                        .show()

                false
            }

            else -> {
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL: String) {
        StyleableToast.makeText(this, "Your image was uploaded succesffuly", R.style.exampleToast)
            .show()
        mUserProfileImageURL = imageURL
        updateUserProfileDetails()
    }


}
