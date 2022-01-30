package com.example.healthrecorder.fragment.list.detail.illnesshistory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.healthrecorder.R
import com.example.healthrecorder.fragment.list.detail.viewmodel.InfoViewModel
import com.example.healthrecorder.model.IllnessHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.android.synthetic.main.fragment_symptoms_illness_history.view.*

class SymptomsIllnessHistory : Fragment() {
    private val infoVm: InfoViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_symptoms_illness_history, container, false)
        val currentUser = FirebaseAuth.getInstance().currentUser.uid
        val documentRef = infoVm.dataset.value
        val db =
            FirebaseFirestore.getInstance().collection("users").document("$currentUser")
                .collection("Illness History").document("$documentRef")


        db.get().addOnSuccessListener { documentSnapShot ->
            val db = documentSnapShot.toObject<IllnessHistory>()

            view.symptom_1.text = db?.illnessSymptoms.toString()


            if (db?.illnessSymptoms != null) {
                view.Symptoms_IllnessVH_CardView.visibility = View.VISIBLE
                view.Symptoms_IllnessVH_NoItem.visibility = View.GONE

            } else {
                view.Symptoms_IllnessVH_CardView.visibility = View.GONE
                view.Symptoms_IllnessVH_NoItem.visibility = View.VISIBLE
            }


        }
        return view
    }
}