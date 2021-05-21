package com.example.healthrecorder.fragment.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthrecorder.R
import com.example.healthrecorder.model.IllnessHistory
import com.example.healthrecorder.model.Medication
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_list_illness_history.view.*
import kotlinx.android.synthetic.main.fragment_list_medication.view.*


private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
private val currentUser = FirebaseAuth.getInstance().currentUser
private val collectionReference: CollectionReference =
    db.collection("users").document(currentUser.uid).collection("Illness History")
var listIllnessHistoryAdapter: ListIllnessHistoryAdapter? = null


class ListIllnessHistory : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_list_illness_history, container, false)
        view.apply {
            val query: Query = collectionReference
            val firestoreRecyclerOptions: FirestoreRecyclerOptions<IllnessHistory> =
                FirestoreRecyclerOptions.Builder<IllnessHistory>()
                    .setQuery(query, IllnessHistory::class.java)
                    .build()

            listIllnessHistoryAdapter =
                ListIllnessHistoryAdapter(firestoreRecyclerOptions, context);

            recyclerView2!!.layoutManager = LinearLayoutManager(requireContext())
            recyclerView2.adapter = listIllnessHistoryAdapter

        }

        view.floatingActionButton2.setOnClickListener {
            val direction =
                ListIllnessHistoryDirections.actionListIllnessHistoryToAddIllnessHistory()
            val controller = findNavController()
            controller.navigate(direction)
        }


        return view
    }

    override fun onStart() {
        super.onStart()
        listIllnessHistoryAdapter!!.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        listIllnessHistoryAdapter!!.stopListening()
    }


}



