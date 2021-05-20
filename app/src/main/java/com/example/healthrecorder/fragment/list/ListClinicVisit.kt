package com.example.healthrecorder.fragment.list

import android.app.AlertDialog
import android.media.MediaPlayer
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.healthrecorder.R
import com.example.healthrecorder.model.ClinicVisit
import com.example.healthrecorder.utils.SwipeToDelete
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_list__clinic__visit.*
import kotlinx.android.synthetic.main.fragment_list__clinic__visit.view.*


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
        val view = inflater.inflate(R.layout.fragment_list__clinic__visit, container, false)
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






