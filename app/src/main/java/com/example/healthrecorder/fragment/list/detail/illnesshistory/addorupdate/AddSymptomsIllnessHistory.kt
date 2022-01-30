package com.example.healthrecorder.fragment.list.detail.illnesshistory.addorupdate

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
import com.example.healthrecorder.model.IllnessHistory
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_add_symptoms_clinic_visit.view.*
import kotlinx.android.synthetic.main.fragment_add_symptoms_illness_history.*

class AddSymptomsIllnessHistory : Fragment() {

    private val args: AddPreventionIllnessHistoryArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_symptoms_illness_history, container, false)
        val currentUser = FirestoreClass().getCurrentUserID()
        val db = FirebaseFirestore.getInstance().collection("users").document(currentUser)
            .collection("Illness History").document("${args.id}")
        db.get().addOnSuccessListener { DocumentSnapshot ->
            val cv = DocumentSnapshot.toObject<IllnessHistory>()
            add_Symptoms_IllnessH.text = cv?.illnessSymptoms?.toEditable()

        }

        view.button_symptoms_clinicVisit.setOnClickListener {
            db.update(
                "illnessSymptoms", "${add_Symptoms_IllnessH.text.toString()}"
            ).addOnCompleteListener {
                val action =
                    AddSymptomsIllnessHistoryDirections.actionAddSymptomsIllnessHistoryToIllnessHistoryTabLayout(
                        args.id
                    )
                val gun = findNavController()
                gun.navigate(action)
            }
        }



        view.bkbutton400.setOnClickListener {
            findNavController().navigate(
                AddSymptomsIllnessHistoryDirections.actionAddSymptomsIllnessHistoryToIllnessHistoryTabLayout(
                    args.id
                )
            )
        }

        return view
    }

}
