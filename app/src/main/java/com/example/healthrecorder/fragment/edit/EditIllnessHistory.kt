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
import com.example.healthrecorder.model.IllnessHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.muddzdev.styleabletoast.StyleableToast
import kotlinx.android.synthetic.main.fragment_edit_illness_history.*
import kotlinx.android.synthetic.main.fragment_edit_illness_history.view.*


class EditIllnessHistory : Fragment() {


    private val args: EditIllnessHistoryArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_illness_history, container, false)
        val docId = args.id
        val mFireStore = FirebaseFirestore.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser
        val docRef = mFireStore.collection("users").document(currentUser.uid)
            .collection("Illness History").document("$docId")

        docRef.get().addOnSuccessListener { documentSnapShot ->
            val illnessHistory = documentSnapShot.toObject<IllnessHistory>()

//
            update_illnessName.text = illnessHistory?.illnessName?.toEditable()
            update_illnessDescription.text = illnessHistory?.illnessDescription?.toEditable()
            update_illnessCause.text = illnessHistory?.illnessCause?.toEditable()

        }

        view.edit_submit_illness_history.setOnClickListener {
            when {
                TextUtils.isEmpty(update_illnessName.text) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Please Enter Illness Name",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(update_illnessDescription.text) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Please Enter Illness Description",
                        R.style.exampleToast
                    ).show()

                TextUtils.isEmpty(update_illnessCause.text) ->
                    StyleableToast.makeText(
                        requireContext(),
                        "Please Enter Illness Cause",
                        R.style.exampleToast
                    ).show()


                else -> {

                    val mFireStore = FirebaseFirestore.getInstance()
                    val currentUser = FirebaseAuth.getInstance().currentUser
                    mFireStore.collection("users").document(currentUser.uid)
                        .collection("Illness History").document("${args.id}").update(
                            "illnessName",
                            "${update_illnessName.text.toString()}",
                            "illnessDescription",
                            "${update_illnessDescription.text.toString()}",
                            "illnessCause",
                            "${update_illnessCause.text.toString()}"
                        )

                    StyleableToast.makeText(
                        requireContext(),
                        "Successfully Updated",
                        R.style.exampleToast
                    ).show()
                    val action =
                        EditIllnessHistoryDirections.actionEditIllnessHistoryToIllnessHistoryTabLayout(
                            args.id
                        )
                    val controller = findNavController()
                    controller.navigate(action)


                }

            }


        }

        view.bkbutton400.setOnClickListener {
            findNavController().navigate(
                EditIllnessHistoryDirections.actionEditIllnessHistoryToIllnessHistoryTabLayout(
                    args.id
                )
            )
        }


        return view


    }


}
