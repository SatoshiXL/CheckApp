package com.example.healthrecorder.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthrecorder.R
import com.example.healthrecorder.fragment.edit.destroyState
import com.example.healthrecorder.model.ClinicVisit
import com.example.healthrecorder.model.Medication
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_list_clinic_visit.view.*
import kotlinx.android.synthetic.main.fragment_list_medication.view.*


class ListMedication : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val collectionReference: CollectionReference =
        db.collection("users").document(currentUser.uid).collection("Medication")
    var listMedicationAdapter: ListMedicationAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_medication, container, false)
        view.apply {
            val query: Query = collectionReference
            val firestoreRecyclerOptions: FirestoreRecyclerOptions<Medication> =
                FirestoreRecyclerOptions.Builder<Medication>()
                    .setQuery(query, Medication::class.java)
                    .build()

            listMedicationAdapter = ListMedicationAdapter(firestoreRecyclerOptions, context);

            recyclerView1!!.layoutManager = LinearLayoutManager(requireContext())
            recyclerView1.adapter = listMedicationAdapter

        }

        view.floatingActionButton_medication.setOnClickListener {
            val direction = ListMedicationDirections.actionListMedicationToAddMedication()
            val controller = findNavController()
            controller.navigate(direction)
        }


        return view
    }

    override fun onStart() {
        super.onStart()
        listMedicationAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        listMedicationAdapter!!.stopListening()
    }




}






