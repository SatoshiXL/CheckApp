package com.example.healthrecorder.fragment.add

import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.healthrecorder.R

import com.example.healthrecorder.fragment.edit.destroyState
import com.example.healthrecorder.model.ClinicVisit
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_add_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_add_clinic_visit.view.*
import java.text.SimpleDateFormat
import java.util.*

class AddClinicVisit : Fragment() {


    private val mFireStore = FirebaseFirestore.getInstance()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_clinic_visit, container, false)
        view?.date_input_layout?.setEndIconOnClickListener {
            MaterialDatePicker.Builder.datePicker().build().let {
                it.show(requireFragmentManager(), it.toString())

                it.addOnPositiveButtonClickListener {
                    Log.d("Dpp", "$it")
                    Calendar.getInstance(TimeZone.getTimeZone("UTC")).let { cal ->
                        cal.time = Date(it)
                        view.add_date.text = Editable.Factory.getInstance().newEditable(
                                "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}/${
                                    cal.get(Calendar.YEAR)
                                }"
                        )
                        //   Log.d("Dpp","${cal.get(Calendar.DAY_OF_MONTH)}//${cal.get(Calendar.MONTH)}//${cal.get(Calendar.YEAR)}")
                    }

                }
            }
        }

        view?.time_input?.setEndIconOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                view.add_time.text = SimpleDateFormat("HH:mm").format(cal.time).toEditable()
            }
            TimePickerDialog(
                    requireContext(),
                    timeSetListener,
                    cal.get(Calendar.HOUR_OF_DAY),
                    cal.get(Calendar.MINUTE),
                    true
            ).show()
        }




        view.add_submit_illness_history.setOnClickListener {

            when {
                TextUtils.isEmpty(add_title.text?.trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(requireContext(), "Title is Empty", R.style.exampleToast).show()

                }

                TextUtils.isEmpty(add_doctor_name.text?.trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(requireContext(), "Doctors Name is Empty", R.style.exampleToast).show()

                }

                TextUtils.isEmpty(add_visit_type.text?.trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(requireContext(), "Visit Type is Empty", R.style.exampleToast).show()

                }

                TextUtils.isEmpty(add_note.text?.trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(requireContext(), "Note is Empty", R.style.exampleToast).show()

                }

                TextUtils.isEmpty(add_date.text?.trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(requireContext(), "Date is Empty", R.style.exampleToast).show()

                }

                TextUtils.isEmpty(add_time.text?.trim() { it <= ' ' }) -> {
                    StyleableToast.makeText(requireContext(), "Time is Empty", R.style.exampleToast).show()

                }

                else -> {
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    val clinicVisit = ClinicVisit(
                            "",
                            add_title.text.toString(),
                            add_doctor_name.text.toString(),
                            add_visit_type.text.toString(),
                            add_note.text.toString(),
                            add_date.text.toString(),
                            add_time.text.toString()
                    )
                    mFireStore.collection("users").document(currentUser.uid)
                            .collection("Clinic Visit")
                            .add(clinicVisit).addOnSuccessListener { documentReference ->
                                val document = documentReference.id
                                mFireStore.collection("users").document(currentUser.uid)
                                        .collection("Clinic Visit").document("$document").update("id", "$document")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding document", e)
                            }

                    val direction = AddClinicVisitDirections.actionAddClinicVisitToListClinicVisit()
                    val controller = findNavController()
                    controller.navigate(direction)
                    destroyState(controller)
                    Toast.makeText(requireContext(), "Successfully Added", Toast.LENGTH_LONG).show()

                }


            }


        }
        return view
    }
}

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)






