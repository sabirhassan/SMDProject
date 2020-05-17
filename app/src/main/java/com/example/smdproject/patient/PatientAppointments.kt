package com.example.smdproject.patient

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
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
class PatientAppointments : Fragment() {

    lateinit var adapter: FirestoreRecyclerAdapter<Request?, approvedViewHolder>
    lateinit var mFirestoreList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    class approvedViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
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
        val view = inflater.inflate(R.layout.patient_approved_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            mFirestoreList = view.findViewById(R.id.RecyclerPatientApprovedList)
            mFirestoreList.layoutManager = LinearLayoutManager(view.context)
            val rootRef = FirebaseFirestore.getInstance()
            val query: Query = rootRef.collection("Requests").whereEqualTo("PatientID",
                CurrentData.patientUser?.Email
            ).whereEqualTo("Status", "Approved")
            val options =
                FirestoreRecyclerOptions.Builder<Request>().setQuery(query, Request::class.java)
                    .build()
            adapter =
                object : FirestoreRecyclerAdapter<Request?, approvedViewHolder>(options) {

                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): approvedViewHolder {
                        val view: View = LayoutInflater.from(parent.context)
                            .inflate(R.layout.patient_pending_object, parent, false)
                        return approvedViewHolder(view)

                    }


                    override fun onBindViewHolder(holder: approvedViewHolder, position: Int, req: Request) {
                        holder!!.doctorName.text = req!!.DoctorName
                        holder.date.text = req.Date
                    }
                }

            mFirestoreList?.setHasFixedSize(true)
            mFirestoreList?.adapter=adapter

        }


        return view
    }

}
