package com.example.healthrecorder.fragment.edit

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.healthrecorder.R
import com.example.healthrecorder.fragment.add.toEditable
import com.example.healthrecorder.model.Medication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_edit_medications.*
import kotlinx.android.synthetic.main.fragment_edit_medications.view.*


class Edit_Medications : Fragment() {

    private val args: Edit_MedicationsArgs by navArgs()
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_medications, container, false)


        val mFireStore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val docRef = mFireStore.collection("users").document(currentUser.uid)
                .collection("Medication").document("${args.id}")
        docRef.get().addOnSuccessListener { documentSnapShot ->
            val medication = documentSnapShot.toObject<Medication>()
//
            edit_medicine_Name.text = medication?.medicineName?.toEditable()
            edit_medicine_Description.text = medication?.medicineDescription?.toEditable()
            edit_usage_instruction.text = medication!!.usageInstruction!!.toEditable()
            edit_dosage.text = medication?.dosage?.toEditable()

        }

        view.bkbutton400.setOnClickListener {
            findNavController().navigate(R.id.action_edit_Medications_to_listMedication)
        }




        view.add_submit_illness_history.setOnClickListener {
            updateData()


        }

        return view

    }

    private fun updateData() {
        when {
            TextUtils.isEmpty(edit_medicine_Name.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(requireContext(), "Medicine Name is Empty", R.style.exampleToast).show()

            }

            TextUtils.isEmpty(edit_medicine_Description.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(requireContext(), "Medicine Description is Empty", R.style.exampleToast).show()

            }

            TextUtils.isEmpty(edit_usage_instruction.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(requireContext(), "Usage instruction is Empty", R.style.exampleToast).show()

            }

            TextUtils.isEmpty(edit_dosage.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(requireContext(), "Dosage is Empty", R.style.exampleToast).show()

            }

            else -> {

                val mFireStore = FirebaseFirestore.getInstance()
                val currentUser = FirebaseAuth.getInstance().currentUser
                val medication = Medication(
                        "${args.id}",
                        edit_medicine_Name.text.toString(),
                        edit_medicine_Description.text.toString(),
                        edit_usage_instruction.text.toString(),
                        edit_dosage.text.toString(),

                        )
                mFireStore.collection("users").document(currentUser.uid)
                        .collection("Medication").document("${args.id}").set(medication)


                val action = Edit_MedicationsDirections.actionEditMedicationsToListMedication()
                val controller = findNavController()
                controller.navigate(action)
                destroyState(controller)


            }
        }

    }


}

