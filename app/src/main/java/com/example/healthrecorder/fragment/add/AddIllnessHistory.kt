package com.example.healthrecorder.fragment.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.healthrecorder.R
import com.example.healthrecorder.fragment.edit.destroyState
import com.example.healthrecorder.model.IllnessHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.custom_row_illness_history.view.*
import kotlinx.android.synthetic.main.fragment_add_illness_history.view.*
import kotlinx.android.synthetic.main.fragment_list_illness_history.view.*


class AddIllnessHistory : Fragment() {


    private val currentUser = FirebaseAuth.getInstance().currentUser.uid
    private val db = Firebase.firestore.collection("users").document("$currentUser")
        .collection("Illness History")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_illness_history, container, false)


        view.add_submit_illness_history.setOnClickListener {
            when {
                TextUtils.isEmpty(view.add_illnessName.text?.trim() { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Please Enter Illness Name",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(view.add_illnessDescription.text?.trim() { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Please Enter Illness Description",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(view.add_illnessCause.text?.trim() { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Please Enter Illness Cause",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(view.add_illnessSymptoms.text?.trim() { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Please Enter Illness Symptoms",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(view.add_prevention.text?.trim() { it <= ' ' }) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Please Enter Prevention",
                        R.style.exampleToast
                    ).show()

                else -> {
                    val illnessHistory = IllnessHistory(
                        "",
                        view.add_illnessName.text.toString(),
                        view.add_illnessDescription.text.toString(),
                        view.add_illnessCause.text.toString(),
                        view.add_illnessSymptoms.text.toString(),
                        view.add_prevention.text.toString()
                    )
                    db.add(illnessHistory).addOnSuccessListener { DocumentReference ->
                        val docID = DocumentReference.id
                        db.document("$docID").update("id", "$docID")
                        val action =
                            AddIllnessHistoryDirections.actionAddIllnessHistoryToListIllnessHistory()
                        val any = findNavController()
                        any.navigate(action)
                        destroyState(any)
                    }
                }
            }
        }


        return view
    }

}
