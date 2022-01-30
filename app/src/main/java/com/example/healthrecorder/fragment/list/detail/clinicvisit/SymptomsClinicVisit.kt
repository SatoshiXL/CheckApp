package com.example.healthrecorder.fragment.list.detail.clinicvisit

import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_detail_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_detail_clinic_visit.view.*
import kotlinx.android.synthetic.main.fragment_symptoms_clinic_visit.view.*


class SymptomsClinicVisit : Fragment() {

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

        val view = inflater.inflate(R.layout.fragment_symptoms_clinic_visit, container, false)

        db.get().addOnSuccessListener { documentSnapShot ->
            val db = documentSnapShot.toObject<ClinicVisit>()

            view.symptom_illness_detail.text = db?.symptoms.toString()

            if(db?.symptoms != null){
                view.info_main_view2.visibility = View.VISIBLE
                view.visibilitycardsymptoms.visibility = View.GONE

            } else {
                view.info_main_view2.visibility = View.GONE
                view.visibilitycardsymptoms.visibility = View.VISIBLE
            }



        }

        return view

    }


}

