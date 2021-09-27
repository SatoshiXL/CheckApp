package com.example.healthrecorder.fragment.list

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.healthrecorder.R
import com.example.healthrecorder.model.ClinicVisit
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList

import kotlinx.android.synthetic.main.custom_row_clinic_visit.view.*


private val currentUser = FirebaseAuth.getInstance().currentUser
private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

class ListClinicVisitAdapter(options: FirestoreRecyclerOptions<ClinicVisit>, val context: Context) :
        FirestoreRecyclerAdapter<ClinicVisit, ListClinicVisitAdapter.ClinicVisitVH>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClinicVisitVH {
        return ClinicVisitVH(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.custom_row_clinic_visit, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ClinicVisitVH, position: Int, model: ClinicVisit) {
        holder.title.text = model.title
        holder.doctorsName.text = model.doctorsName
        holder.date.text = model.date
        holder.time.text = model.time
        holder.itemView.delete_icon.setOnClickListener {
            deleteUser(model.id.toString(), model.title.toString())


        }

        holder.itemView.row.setOnClickListener {

            val id = model.id.toString()
            Log.i("this id", "is $id")
            val action = ListClinicVisitDirections.actionListClinicVisitToClinicVisitTabLayout(id)
            holder.itemView.findNavController().navigate(action)


        }


    }

    class ClinicVisitVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val doctorsName: TextView = itemView.findViewById(R.id.doctorsName)
        val date: TextView = itemView.findViewById(R.id.view_date)
        val time: TextView = itemView.findViewById(R.id.view_time)

    }

    private fun deleteData(id: String) {
        db.collection("users").document(currentUser.uid).collection("Clinic Visit")
                .document("$id").delete()
    }


    private fun deleteUser(id: String, title: String) {
        context.apply {
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("Yes") { _, _ ->


                deleteData(id)
                Toast.makeText(
                        context,
                        "Successfully removed $title",
                        Toast.LENGTH_SHORT
                ).show()

            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete ${title}?")
            builder.setMessage(
                    "Are you sure you want to delete $title?"
            )
            builder.create().show()
        }
    }


}

