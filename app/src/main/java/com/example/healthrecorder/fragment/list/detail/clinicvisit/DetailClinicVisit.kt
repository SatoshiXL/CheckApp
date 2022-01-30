package com.example.healthrecorder.fragment.list.detail.clinicvisit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.healthrecorder.R
import com.example.healthrecorder.fragment.list.detail.viewmodel.InfoViewModel
import com.example.healthrecorder.model.ClinicVisit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.custom_row_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_detail_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_detail_clinic_visit.view.*
import kotlinx.android.synthetic.main.fragment_doctors_information.view.*
import kotlin.math.log


class DetailClinicVisit : Fragment() {
    private val infoVm: InfoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val currentUser = FirebaseAuth.getInstance().currentUser.uid
        val documentRef = infoVm.dataset.value
        val db =
            FirebaseFirestore.getInstance().collection("users").document("$currentUser")
                .collection("Clinic Visit").document("$documentRef")

        val view = inflater.inflate(R.layout.fragment_detail_clinic_visit, container, false)

        db.get().addOnSuccessListener { documentSnapShot ->
            val db = documentSnapShot.toObject<ClinicVisit>()
            view.detail_DoctorsName.text = db?.doctorsName.toString()
            detail_visit.text = db?.visitType.toString()
            detail_note.text = db?.note.toString()
            detail_clinicname.text = db?.clinicName.toString()

        }

        return view

    }


}

