package com.example.smdproject.patient

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smdproject.R
import com.example.smdproject.model.CurrentData
import com.example.smdproject.model.Doctor
import com.example.smdproject.model.Request
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.patient_pending_object.view.*

/**
 * A simple [Fragment] subclass.
 */
class PatientPendings : Fragment() {

    lateinit var adapter: FirestoreRecyclerAdapter<Request?, RequestsViewHolder>
    lateinit var mFirestoreList: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    class RequestsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val doctorName: TextView = view.DoctorName
        val date: TextView = view.AppointmentDate
        val cancelBtn: Button = view.cancelbtn
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        if (adapter != null) {
            adapter.stopListening()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.patient_pending_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            mFirestoreList = view.findViewById(R.id.RecyclerPatientPendingList)
            mFirestoreList.layoutManager = LinearLayoutManager(view.context)
            val rootRef = FirebaseFirestore.getInstance()
            val query: Query = rootRef.collection("Requests").whereEqualTo("PatientID",
                CurrentData.patientUser?.Email
            ).whereEqualTo("Status", "Pending")
            val options =
                FirestoreRecyclerOptions.Builder<Request>().setQuery(query, Request::class.java)
                    .build()
            adapter =
                object : FirestoreRecyclerAdapter<Request?, RequestsViewHolder>(options) {

                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): RequestsViewHolder {
                        val view: View = LayoutInflater.from(parent.context)
                            .inflate(R.layout.patient_pending_object, parent, false)
                        return RequestsViewHolder(view)

                    }


                    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int, req: Request) {
                        holder!!.doctorName.text = req!!.DoctorName
                        holder.date.text = req.Date
                        holder.cancelBtn.setOnClickListener{
                            val id =
                                adapter.snapshots.getSnapshot(position).id

                            val reqRef = rootRef.collection("Requests").document(id)
                            reqRef
                                .update("Status", "Canceled")
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

                            adapter.notifyDataSetChanged();
                        }
                    }
                }

            mFirestoreList?.setHasFixedSize(true)
            mFirestoreList?.adapter=adapter

        }


        return view
        }

}
