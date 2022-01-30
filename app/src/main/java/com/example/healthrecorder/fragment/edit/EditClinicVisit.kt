package com.example.healthrecorder.fragment.edit

import android.app.TimePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.healthrecorder.R
import com.example.healthrecorder.fragment.add.toEditable
import com.example.healthrecorder.model.ClinicVisit
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.toObject
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_add_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_add_clinic_visit.view.*
import kotlinx.android.synthetic.main.fragment_edit_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_edit_clinic_visit.view.*
import java.text.SimpleDateFormat
import java.util.*


class EditClinicVisit : Fragment(R.layout.fragment_edit_clinic_visit) {

    private val args: EditClinicVisitArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        update_date_input_layout?.setEndIconOnClickListener {
            MaterialDatePicker.Builder.datePicker().build().let {
                it.show(requireFragmentManager(), it.toString())

                it.addOnPositiveButtonClickListener {
                    Log.d("Dpp", "$it")
                    Calendar.getInstance(TimeZone.getTimeZone("UTC")).let { cal ->
                        cal.time = Date(it)
                        update_date.text = Editable.Factory.getInstance().newEditable(
                            "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${
                                cal.get(Calendar.YEAR)
                            }"
                        )
                        //   Log.d("Dpp","${cal.get(Calendar.DAY_OF_MONTH)}//${cal.get(Calendar.MONTH)}//${cal.get(Calendar.YEAR)}")
                    }

                }
            }
        }

        update_time_input?.setEndIconOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                update_time.text = SimpleDateFormat("HH:mm").format(cal.time).toEditable()
            }
            TimePickerDialog(
                requireContext(),
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        val mFireStore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val docRef = mFireStore.collection("users").document(currentUser.uid)
            .collection("Clinic Visit").document("${args.id}")
        docRef.get().addOnSuccessListener { documentSnapShot ->
            val clinicVisit = documentSnapShot.toObject<ClinicVisit>()
//            update_clinicname.text = clinicVisit.id?.toEditable()
//            val new = clinicVisit?.id.toString()

            update_title.text = clinicVisit?.title?.toEditable()
            update_doctor_name.text = clinicVisit?.doctorsName?.toEditable()
            update_visit_type.text = clinicVisit?.visitType?.toEditable()
            update_note.text = clinicVisit?.note?.toEditable()
            update_date.text = clinicVisit?.date?.toEditable()
            update_time.text = clinicVisit?.time?.toEditable()
            update_clinicName.text = clinicVisit?.clinicName?.toEditable()


        }




        update_button.setOnClickListener {
            updateData()

            val action =
                EditClinicVisitDirections.actionEditClinicVisitToClinicVisitTabLayout(args.id)
            val controller = findNavController()
            controller.navigate(action)
            onDestroy()


        }

    }

    private fun updateData() {
        when {
            TextUtils.isEmpty(update_title.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(requireContext(), "Title is Empty", R.style.exampleToast)
                    .show()

            }

            TextUtils.isEmpty(update_doctor_name.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(
                    requireContext(),
                    "Doctors Name is Empty",
                    R.style.exampleToast
                ).show()

            }

            TextUtils.isEmpty(update_visit_type.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(
                    requireContext(),
                    "Visit Type is Empty",
                    R.style.exampleToast
                ).show()

            }

            TextUtils.isEmpty(update_note.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(requireContext(), "Note is Empty", R.style.exampleToast)
                    .show()

            }

            TextUtils.isEmpty(update_date.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(requireContext(), "Date is Empty", R.style.exampleToast)
                    .show()

            }

            TextUtils.isEmpty(update_time.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(requireContext(), "Time is Empty", R.style.exampleToast)
                    .show()

            }

            TextUtils.isEmpty(update_clinicName.text?.trim() { it <= ' ' }) -> {
                StyleableToast.makeText(
                    requireContext(),
                    "Clinic Name is Empty",
                    R.style.exampleToast
                )
                    .show()

            }

            else -> {

                val mFireStore = FirebaseFirestore.getInstance()
                val currentUser = FirebaseAuth.getInstance().currentUser
                /*val clinicVisit = ClinicVisit(
                    "${args.id}",
                    update_title.text.toString(),
                    update_doctor_name.text.toString(),
                    update_visit_type.text.toString(),
                    update_note.text.toString(),
                    update_date.text.toString(),
                    update_time.text.toString(),
                )*/
                mFireStore.collection("users").document(currentUser.uid)
                    .collection("Clinic Visit").document("${args.id}")
                    .update(
                        "title",
                        "${update_title.text.toString()}",
                        "doctorsName",
                        "${update_doctor_name.text.toString()}",
                        "visitType",
                        "${update_visit_type.text.toString()}",
                        "note",
                        "${update_note.text.toString()}",
                        "date",
                        "${update_date.text.toString()}",
                        "time",
                        "${update_time.text.toString()}",
                        "clinicName",
                        "${update_clinicName.text.toString()}"
                    )


            }
        }

    }


}


fun destroyState(controller: NavController) {
    controller.apply {


    }
}

