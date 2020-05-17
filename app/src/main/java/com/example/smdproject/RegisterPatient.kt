package com.example.smdproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register_patient.*

class RegisterPatient : AppCompatActivity() {

    lateinit var hgt: EditText
    lateinit var weight: EditText
    lateinit var age: EditText
    private lateinit var patientbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_patient)
        val db = Firebase.firestore




        weight=findViewById(R.id.Weight)

        hgt=findViewById(R.id.HeightPatient)


        age=findViewById(R.id.Age)

        patientbtn=findViewById(R.id.registerasPat)
        patientbtn.setOnClickListener {
            val name = username.text.toString().trim()
            val email = Email.text.toString().trim()
            val phone = Phone.text.toString().trim()
            val pass = Password.text.toString().trim()
            val gender = Gender.selectedItem.toString().trim()
            val bloodgrp = bloodgrp.selectedItem.toString().trim()
            val weight1 = Weight.text.toString().trim()
            val hgt1 = HeightPatient.text.toString().trim()
            val specialperson = disability.selectedItem.toString().trim()
            val age1 = Age.text.toString().trim()
            if (name.isEmpty()) {
                username.error = "Name required"
                username.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                Email.error = "Email required"
                Email.requestFocus()
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                Phone.error = "Phone required"
                Phone.requestFocus()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                Password.error = "Password required"
                Password.requestFocus()
                return@setOnClickListener
            }
            if (weight1.isEmpty()) {
                Weight.error = "Weight required"
                Weight.requestFocus()
                return@setOnClickListener
            }
            if (hgt1.isEmpty()) {
                HeightPatient.error = "hgt required"
                HeightPatient.requestFocus()
                return@setOnClickListener
            }
            if (age1.isEmpty()) {
                Age.error = "Age required"
                Age.requestFocus()
                return@setOnClickListener
            }


            db.collection("Users")
                .whereEqualTo("Email", email)
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.isEmpty)
                    {
                        val data2 = hashMapOf(
                            "Name" to name,
                            "Phone" to phone,
                            "Password" to pass,
                            "Email" to email,
                            "BloodGroup" to bloodgrp,
                            "Height" to hgt1,
                            "Weight" to weight1,
                            "Age" to age1,
                            "Gender" to gender,
                            "Disability" to specialperson

                        )
                        db.collection("Users").add(data2)
                        Toast.makeText(applicationContext,"Successfully Registered! Login to Continue",
                            Toast.LENGTH_SHORT).show()
                        intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                        startActivity(intent)
                        finish()
                    }
                    else
                    {
                        Email.error = "Email already exists!"
                        Email.requestFocus()
                    }

                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }

        }


    }
}
