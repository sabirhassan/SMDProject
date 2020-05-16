package com.example.smdproject.patient

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.smdproject.R
import com.example.smdproject.model.CurrentData
import com.example.smdproject.model.Doctor
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_book_appointment.*
import java.util.*


class BookAppointment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        val d1 = intent.getSerializableExtra("Doctor") as Doctor

        name.setText(d1.Name)
        expertise.setText(d1.Speciality)
        experience.setText(d1.Experience)
        Address.setText(d1.Address)
        Bio.setText(d1.Bio)



        val tvdate = findViewById<TextView>(R.id.datePicker1)
        val datebtn = findViewById<Button>(R.id.datePickerbtn)
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        datebtn.setOnClickListener {

            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in TextView
                tvdate.setText("" + dayOfMonth + "/" + month + "/" + year)
            }, year, month, day)

            dpd.datePicker.minDate=c.timeInMillis
            c.add(Calendar.DATE,30)
            dpd.datePicker.maxDate=c.timeInMillis

            dpd.show()
        }


        btnRequest.setOnClickListener{
            if(tvdate.text.toString().isEmpty())
            {
                Toast.makeText(this,"Enter Date of Appointment", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val Date = tvdate.text.toString()
            val DoctorID = d1.Email
            val DoctorName = d1.Name
            val PatientID = CurrentData.patientUser?.Email
            val Status = "Pending"

            val db = Firebase.firestore
            val data2 = hashMapOf(
                "Date" to Date,
                "DoctorID" to DoctorID,
                "PatientID" to PatientID,
                "Status" to Status,
                "DoctorName" to DoctorName
            )
            db.collection("Requests").add(data2)
            Toast.makeText(applicationContext,"Request Sent!",Toast.LENGTH_SHORT).show()
            intent = Intent(this, PatientHome::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
            startActivity(intent)
            finish()
        }



    }
}
