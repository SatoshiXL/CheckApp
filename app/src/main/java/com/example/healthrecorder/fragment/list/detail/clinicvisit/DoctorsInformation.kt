package com.example.healthrecorder.fragment.list.detail.clinicvisit

import android.os.Bundle
import android.text.TextUtils
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
import kotlinx.android.synthetic.main.fragment_add_doctor_information.view.*
import kotlinx.android.synthetic.main.fragment_detail_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_detail_clinic_visit.view.*
import kotlinx.android.synthetic.main.fragment_doctors_information.*
import kotlinx.android.synthetic.main.fragment_doctors_information.view.*


class DoctorsInformation : Fragment() {

    private val infoVm: InfoViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_doctors_information, container, false)
        val currentUser = FirebaseAuth.getInstance().currentUser.uid
        val documentRef = infoVm.dataset.value
        val db =
            FirebaseFirestore.getInstance().collection("users").document("$currentUser")
                .collection("Clinic Visit").document("$documentRef")



        db.get().addOnSuccessListener { documentSnapShot ->
            val db = documentSnapShot.toObject<ClinicVisit>()
            info_doctorsname.text = db?.doctorsName.toString()
            info_doctorsEmail.text = db?.doctorsEmailID.toString()
            info_doctorscontact.text = db?.doctorsPhoneNumber.toString()
            doctorsAddress.text = db?.doctorsAddress.toString()

            if (db?.doctorsEmailID != null) {
                view.info_main_view.visibility = View.VISIBLE
                view.visisblecard.visibility = View.INVISIBLE
            }else{
                view.info_main_view.visibility = View.INVISIBLE
                view.visisblecard.visibility = View.VISIBLE
            }


        }





        return view

    }


}

