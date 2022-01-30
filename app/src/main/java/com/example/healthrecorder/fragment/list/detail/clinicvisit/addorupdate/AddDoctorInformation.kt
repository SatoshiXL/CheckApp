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
import kotlinx.android.synthetic.main.fragment_add_doctor_information.*
import kotlinx.android.synthetic.main.fragment_add_doctor_information.view.*


class AddDoctorInformation : Fragment() {

    private val args: AddDoctorInformationArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_doctor_information, container, false)


        val currentUser = FirestoreClass().getCurrentUserID()
        val db = FirebaseFirestore.getInstance().collection("users").document(currentUser)
            .collection("Clinic Visit").document("${args.id}")
        db.get().addOnSuccessListener { DocumentSnapshot ->
            val cv = DocumentSnapshot.toObject<ClinicVisit>()
            add_doctor_email.text = cv?.doctorsEmailID?.toEditable()
            add_doctors_phone.text = cv?.doctorsPhoneNumber?.toEditable()
            add_doctors_address.text = cv?.doctorsAddress?.toEditable()
        }



        view.add_submit_doctors_info.setOnClickListener {
            db.update(
                "doctorsEmailID",
                "${add_doctor_email.text}",
                "doctorsPhoneNumber",
                "${add_doctors_phone.text}",
                "doctorsAddress",
                "${add_doctors_address.text}"
            ).addOnCompleteListener {
                val action =
                    AddDoctorInformationDirections.actionAddDoctorInformationToClinicVisitTabLayout2(
                        args.id
                    )
                val gun = findNavController()
                gun.navigate(action)
            }
        }

        view.bkbutton400.setOnClickListener {
            findNavController().navigate(
                AddDoctorInformationDirections.actionAddDoctorInformationToClinicVisitTabLayout2(
                    args.id
                )
            )
        }


        return view
    }
}