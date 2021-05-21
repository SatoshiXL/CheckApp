package com.example.healthrecorder.fragment.list

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.healthrecorder.R
import com.example.healthrecorder.model.IllnessHistory
import com.example.healthrecorder.model.Medication
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.custom_row_illness_history.view.*
import kotlinx.android.synthetic.main.custom_row_medication.view.*


private val currentUser = FirebaseAuth.getInstance().currentUser
private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

class ListIllnessHistoryAdapter(
    options: FirestoreRecyclerOptions<IllnessHistory>,
    val context: Context
) :
    FirestoreRecyclerAdapter<IllnessHistory, ListIllnessHistoryAdapter.ListIllnessHistoryVH>(options) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListIllnessHistoryVH {
        return ListIllnessHistoryVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_row_illness_history, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ListIllnessHistoryVH,
        position: Int,
        model: IllnessHistory
    ) {
        holder.illnessName.text = model.illnessName.toString()
        holder.illnessDescription.text = model.illnessDescription.toString()
        holder.illnessCause.text = model.illnessCause.toString()
        holder.illnessSymptoms.text = model.illnessSymptoms.toString()
        holder.prevention.text = model.prevention.toString()

        holder.itemView.delete_icon_illness_history.setOnClickListener {
            deleteUser(model.id.toString(), model.illnessName.toString())


        }

        holder.itemView.row_illnessHistory.setOnClickListener {
            val action =
                ListIllnessHistoryDirections.actionListIllnessHistoryToEditIllnessHistory(model.id.toString())
            val controller = holder.itemView.findNavController()
            controller.navigate(action)
        }


    }

    class ListIllnessHistoryVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val illnessName: TextView = itemView.findViewById(R.id.illness_name_row)
        val illnessDescription: TextView = itemView.findViewById(R.id.illnessDescription_row)
        val illnessCause: TextView = itemView.findViewById(R.id.illness_cause_row)
        val illnessSymptoms: TextView = itemView.findViewById(R.id.illness_symptoms_row)
        val prevention: TextView = itemView.findViewById(R.id.prevention_row)


    }

    private fun deleteData(id: String) {
        db.collection("users").document(currentUser.uid).collection("Illness History")
            .document("$id").delete()
    }


    private fun deleteUser(id: String, illnessName: String) {
        context.apply {
            val builder = AlertDialog.Builder(this)
            builder.setPositiveButton("Yes") { _, _ ->


                deleteData(id)
                Toast.makeText(
                    context,
                    "Successfully removed $illnessName",
                    Toast.LENGTH_SHORT
                ).show()

            }
            builder.setNegativeButton("No") { _, _ -> }
            builder.setTitle("Delete $illnessName?")
            builder.setMessage(
                "Are you sure you want to delete $illnessName?"
            )
            builder.create().show()
        }
    }


}

