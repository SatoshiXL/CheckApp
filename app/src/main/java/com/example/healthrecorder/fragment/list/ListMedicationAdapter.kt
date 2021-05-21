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
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.healthrecorder.R
import com.example.healthrecorder.model.ClinicVisit
import com.example.healthrecorder.model.Medication
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.custom_row_clinic_visit.view.*
import kotlinx.android.synthetic.main.custom_row_medication.view.*


private val currentUser = FirebaseAuth.getInstance().currentUser
private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

class ListMedicationAdapter(options: FirestoreRecyclerOptions<Medication>, val context: Context) :
    FirestoreRecyclerAdapter<Medication, ListMedicationAdapter.ListMedicationVH>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListMedicationVH {
        return ListMedicationVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.custom_row_medication, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ListMedicationVH, position: Int, model: Medication) {
        holder.medicineName.text = model.medicineName.toString()
        holder.medicineDescription.text = model.medicineDescription.toString()
        holder.usageInstruction.text = model.usageInstruction.toString()
        holder.dosage.text = model.dosage.toString()
        holder.itemView.delete_icon_row.setOnClickListener {
            deleteUser(model.id.toString(), model.medicineName.toString())


        }

        holder.itemView.row_medication.setOnClickListener {

            val id = model.id.toString()
            val action = ListMedicationDirections.actionListMedicationToEditMedications(id)
            holder.itemView.findNavController().navigate(action)



        }


    }

    class ListMedicationVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val medicineName: TextView = itemView.findViewById(R.id.medicineName_row)
        val medicineDescription: TextView = itemView.findViewById(R.id.medicineDescription_row)
        val usageInstruction: TextView = itemView.findViewById(R.id.usageInstruction)
        val dosage: TextView = itemView.findViewById(R.id.dosage_row)


    }

    private fun deleteData(id: String) {
        db.collection("users").document(currentUser.uid).collection("Medication")
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