package com.example.smdproject.doctor

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.smdproject.R
import com.example.smdproject.model.CurrentData
import com.example.smdproject.model.Doctor
import com.example.smdproject.model.Request
import com.example.smdproject.patient.BookAppointment
import com.example.smdproject.patient.PatientRequests
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.appointment_object.view.*
import kotlinx.android.synthetic.main.doctor_request_object.view.*

/**
 * A simple [Fragment] subclass.
 */
class DoctorRequests : Fragment() {

    lateinit var adapter: FirestoreRecyclerAdapter<Request?, RequestViewHolder?>
    private var firestoreDB: FirebaseFirestore? = null
    lateinit var mFirestoreList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    class RequestViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val patientName: TextView = view.PatientName
        val date: TextView = view.AppointmentDate
        val acpBtn: Button = view.acceptBtn
        val rejBtn: Button = view.rejectBtn
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
        val view = inflater.inflate(R.layout.appointment_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            mFirestoreList = view.findViewById(R.id.RecyclerDoctorRequestList)
            mFirestoreList.layoutManager = LinearLayoutManager(view.context)
            val rootRef = FirebaseFirestore.getInstance()
            val query: Query = rootRef.collection("Requests").whereEqualTo("DoctorID",
                CurrentData.doctorUser?.Email
            ).whereEqualTo("Status","pending")
            val options = FirestoreRecyclerOptions.Builder<Request>().setQuery(query, Request::class.java).build()

            adapter =
                object : FirestoreRecyclerAdapter<Request?, RequestViewHolder?>(options) {

                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): RequestViewHolder {
                        val view: View = LayoutInflater.from(parent.context)
                            .inflate(R.layout.doctor_request_object, parent, false)
                        return RequestViewHolder(view)

                    }


                    override fun onBindViewHolder(holder: RequestViewHolder, position: Int, req: Request) {
                        holder!!.patientName.text = req!!.PatientID
                        holder.date.text = req.Date
                        holder.acpBtn.setOnClickListener{
                            val id =
                                adapter.snapshots.getSnapshot(position).id

                            val reqRef = rootRef.collection("Requests").document(id)
                            reqRef
                                .update("Status", "Accepted")
                                .addOnSuccessListener { Log.d("TAG", "DocumentSnapshot successfully updated!") }
                                .addOnFailureListener { e -> Log.w("TAG", "Error updating document", e) }

                            adapter.notifyDataSetChanged();
                        }
                        holder.rejBtn.setOnClickListener{
                            val id =
                                adapter.snapshots.getSnapshot(position).id

                            val reqRef = rootRef.collection("Requests").document(id)
                            reqRef
                                .update("Status", "Rejected")
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
