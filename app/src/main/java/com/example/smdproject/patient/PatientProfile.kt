package com.example.smdproject.patient

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Spinner

import com.example.smdproject.R
import com.example.smdproject.model.CurrentData

/**
 * A simple [Fragment] subclass.
 */
class PatientProfile : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_patient_profile, container, false)

        val name = view.findViewById<EditText>(R.id.username)
        val email = view.findViewById<EditText>(R.id.Email)
        val age = view.findViewById<EditText>(R.id.Age)
        val phone = view.findViewById<EditText>(R.id.Phone)
        val password = view.findViewById<EditText>(R.id.Password)
        val bg = view.findViewById<Spinner>(R.id.BloodGroup)
        val disability = view.findViewById<Spinner>(R.id.Disability)
        val height = view.findViewById<EditText>(R.id.Height)
        val weight = view.findViewById<EditText>(R.id.Weight)
        val gender = view.findViewById<Spinner>(R.id.Gender)

        name.setText(CurrentData.patientUser?.Name)
        email.setText(CurrentData.patientUser?.Email)
        age.setText(CurrentData.patientUser?.Age)
        phone.setText(CurrentData.patientUser?.Phone)
        password.setText(CurrentData.patientUser?.Password)
//        bg.setText(CurrentData.patientUser?.BloodGroup)
//        disability.setText(CurrentData.patientUser?.Disability)
        height.setText(CurrentData.patientUser?.Height)
        weight.setText(CurrentData.patientUser?.Weight)
//        gender.setText(CurrentData.patientUser?.Gender)



        return view
    }

}
