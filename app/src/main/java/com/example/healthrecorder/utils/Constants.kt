package com.example.healthrecorder.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
import android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
import android.webkit.MimeTypeMap

object Constants {
    const val USERS: String = "users"
    const val READ_STORAGE_PERMISSION_CODE = 1
    const val PICK_IMAGE_REQUEST_CODE = 2
    const val USER_PROFILE_IMAGE:String = "User_Profile_Image"
    const val IMAGE:String = "image"
    const val COMPLETE_PROFILE:String = "profileCompleted"


    fun showImageChooser(activity: Activity){
        val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }

    fun getFileExtension(activity: Activity, uri: Uri?): String?{

        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }
}