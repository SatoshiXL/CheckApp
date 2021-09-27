package com.example.healthrecorder.fragment.list.detail.clinicvisit.addorupdate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.healthrecorder.R
import com.example.healthrecorder.firestore.FirestoreClass
import com.example.healthrecorder.fragment.add.toEditable
import com.example.healthrecorder.model.ClinicVisit
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_add_symptoms_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_add_symptoms_clinic_visit.view.*
import kotlinx.android.synthetic.main.fragment_add_symptoms_clinic_visit.view.bkbutton400


class AddSymptomsClinicVisit : Fragment() {

    private val args: AddSymptomsClinicVisitArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_symptoms_clinic_visit, container, false)

        val currentUser = FirestoreClass().getCurrentUserID()
        val db = FirebaseFirestore.getInstance().collection("users").document(currentUser)
            .collection("Clinic Visit").document("${args.id}")
        db.get().addOnSuccessListener { DocumentSnapshot ->
            val cv = DocumentSnapshot.toObject<ClinicVisit>()
            add_Symptoms_CD.text = cv?.symptoms?.toEditable()

        }



        view.button_symptoms_clinicVisit.setOnClickListener {
            db.update(
                "symptoms", "${add_Symptoms_CD.text.toString()}"
            ).addOnCompleteListener {
                val action =
                    AddSymptomsClinicVisitDirections.actionAddSymptomsClinicVisitToClinicVisitTabLayout(
                        args.id
                    )
                val gun = findNavController()
                gun.navigate(action)

            }
        }



        view.bkbutton400.setOnClickListener {
            findNavController().navigate(
                AddSymptomsClinicVisitDirections.actionAddSymptomsClinicVisitToClinicVisitTabLayout(
                    args.id
                )
            )
        }

        return view
    }

}