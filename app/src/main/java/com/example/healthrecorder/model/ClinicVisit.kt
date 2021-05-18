package com.example.healthrecorder.model

import com.google.firebase.firestore.DocumentId


class ClinicVisit(
    val id:String? = null,
    val title:String? = null,
    val doctorsName: String? = null,
    val visitType: String? = null,
    val note: String? = null,
    val date: String? = null,
    val time: String? = null,
)

