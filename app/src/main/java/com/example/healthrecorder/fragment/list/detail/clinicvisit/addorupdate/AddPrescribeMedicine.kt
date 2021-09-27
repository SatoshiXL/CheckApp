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
import kotlinx.android.synthetic.main.fragment_add_prescribe_medicine.*
import kotlinx.android.synthetic.main.fragment_add_prescribe_medicine.view.*
import kotlinx.android.synthetic.main.fragment_add_prescribe_medicine.view.bkbutton


class AddPrescribeMedicine : Fragment() {

    private val args: AddPrescribeMedicineArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_prescribe_medicine, container, false)


        view.bkbutton.setOnClickListener {
            findNavController().navigate(
                AddPrescribeMedicineDirections.actionAddPrescribeMedicineToClinicVisitTabLayout(
                    args.id
                )
            )
        }


        val currentUser = FirestoreClass().getCurrentUserID()
        val db = FirebaseFirestore.getInstance().collection("users").document(currentUser)
            .collection("Clinic Visit").document("${args.id}")
        db.get().addOnSuccessListener { DocumentSnapshot ->
            val cv = DocumentSnapshot.toObject<ClinicVisit>()
            add_medicine_name_CD.text = cv?.medicineName?.toEditable()
            add_medicine_Description_CD.text = cv?.medicineDescription?.toEditable()
            add_dosage_CD.text = cv?.dosage?.toEditable()
        }



        view.submit_CD_medicine.setOnClickListener {
            db.update(
                "medicineName",
                "${add_medicine_name_CD.text.toString()}",
                "medicineDescription",
                "${add_medicine_Description_CD.text.toString()}",
                "dosage",
                "${add_dosage_CD.text.toString()}"
            ).addOnCompleteListener {
                val action =
                    AddPrescribeMedicineDirections.actionAddPrescribeMedicineToClinicVisitTabLayout(
                        args.id
                    )
                val gun = findNavController()
                gun.navigate(action)
            }
        }

        return view
    }
}