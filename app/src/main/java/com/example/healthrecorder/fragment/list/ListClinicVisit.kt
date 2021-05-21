package com.example.healthrecorder.fragment.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthrecorder.R
import com.example.healthrecorder.model.ClinicVisit
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_list_clinic_visit.*
import kotlinx.android.synthetic.main.fragment_list_clinic_visit.view.*


class ListClinicVisit : Fragment() {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val collectionReference: CollectionReference =
        db.collection("users").document(currentUser.uid).collection("Clinic Visit")
    var listClinicVisitAdapter: ListClinicVisitAdapter? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_clinic_visit, container, false)
        view.apply {
            val query: Query = collectionReference
            val firestoreRecyclerOptions: FirestoreRecyclerOptions<ClinicVisit> =
                FirestoreRecyclerOptions.Builder<ClinicVisit>()
                    .setQuery(query, ClinicVisit::class.java)
                    .build()

            listClinicVisitAdapter = ListClinicVisitAdapter(firestoreRecyclerOptions,context);

            recyclerView!!.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = listClinicVisitAdapter

        }

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listClinicVisit_to_addClinicVisit)

        }

        return view
    }

    override fun onStart() {
        super.onStart()
        listClinicVisitAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        listClinicVisitAdapter!!.stopListening()
    }


}






