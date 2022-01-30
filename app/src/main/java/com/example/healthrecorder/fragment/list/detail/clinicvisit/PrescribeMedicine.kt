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
import kotlinx.android.synthetic.main.fragment_prescribe_medicine.view.*
import kotlinx.android.synthetic.main.fragment_symptoms_clinic_visit.view.*


class PrescribeMedicine : Fragment() {
    private val infoVm: InfoViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_prescribe_medicine, container, false)

        val currentUser = FirebaseAuth.getInstance().currentUser.uid
        val documentRef = infoVm.dataset.value
        val db =
            FirebaseFirestore.getInstance().collection("users").document("$currentUser")
                .collection("Clinic Visit").document("$documentRef")


        db.get().addOnSuccessListener { documentSnapShot ->
            val db = documentSnapShot.toObject<ClinicVisit>()

            view.medicine_name.text = db?.medicineName.toString()
            view.medicine_prescription.text = db?.medicineDescription.toString()
            view.dosage.text = db?.dosage.toString()

            if (db?.medicineName != null) {
                view.prescribe_main_view.visibility = View.VISIBLE
                view.visisblecard.visibility = View.GONE

            } else {
                view.prescribe_main_view.visibility = View.GONE
                view.visisblecard.visibility = View.VISIBLE
            }


        }
        return view
    }
}

