package com.example.smdproject.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.example.smdproject.R
import com.example.smdproject.model.Doctor
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 */
class PatientAppointments : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_appointments, container, false)
    }

}
