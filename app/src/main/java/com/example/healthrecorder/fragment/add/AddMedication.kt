package com.example.healthrecorder.fragment.add

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.healthrecorder.R
import com.example.healthrecorder.firestore.FirestoreClass
import com.example.healthrecorder.fragment.edit.destroyState
import com.example.healthrecorder.model.Medication
import com.google.firebase.firestore.FirebaseFirestore
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_add_medication.*
import kotlinx.android.synthetic.main.fragment_add_medication.view.*


class AddMedication : Fragment() {

    val currentUser = FirestoreClass().getCurrentUserID()
    private val mFireStore = FirebaseFirestore.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_medication, container, false)


        view.add_submit_illness_history.setOnClickListener {

            when {
                TextUtils.isEmpty(view.add_medicine_Name.text?.trim { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Medicine Name is Empty",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(view.add_medicine_Description.text?.trim { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Description is Empty",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(view.add_usage_instruction.text?.trim { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Usage is Empty",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(view.add_dosage.text?.trim { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Dosage is Empty",
                        R.style.exampleToast
                    ).show()


                else -> {
                    addMedication(
                        add_medicine_Name.text.toString(),
                        add_medicine_Description.text.toString(),
                        add_usage_instruction.text.toString(),
                        add_dosage.text.toString()
                    )

                }

            }


        }


        return view

    }

    private fun addMedication(
        medicineName: String,
        medicineDescription: String,
        UsageInstruction: String,
        Dosage: String
    ) {
        val medication = Medication(
            "", medicineName, medicineDescription, UsageInstruction, Dosage
        )

        mFireStore.collection("users").document("$currentUser").collection("Medication")
            .add(medication).addOnSuccessListener { documentReferences ->
                val document = documentReferences.id
                Log.i("The document is", "$document")
                mFireStore.collection("users").document("$currentUser").collection("Medication")
                    .document("$document").update("id", "$document").addOnSuccessListener {
                        val action = AddMedicationDirections.actionAddMedicationToListMedication()
                        val controllerX = findNavController()
                        controllerX.navigate(action)
                        destroyState(controllerX)

                    }

            }
    }


}