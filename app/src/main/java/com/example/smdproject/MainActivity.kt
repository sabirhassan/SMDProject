package com.example.smdproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.smdproject.doctor.DoctorHome
import com.example.smdproject.model.CurrentData
import com.example.smdproject.model.Doctor
import com.example.smdproject.model.User
import com.example.smdproject.patient.PatientHome
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var patient: TextView
    lateinit var doctor: TextView
    lateinit var loginbtn: Button
    val db = Firebase.firestore



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        patient=findViewById(R.id.patient_reg)
        doctor=findViewById(R.id.doctor_reg)
        loginbtn = findViewById(R.id.loginbtn)

        loginbtn.setOnClickListener() {
            val mail = user.text.toString().trim()
            val pass = password.text.toString().trim()

            if (mail.isEmpty()) {
                user.error = "first name required"
                user.requestFocus()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                password.error = "password required"
                password.requestFocus()
                return@setOnClickListener
            }

            db.collection("Doctors")
                .whereEqualTo("Email", mail)
                .whereEqualTo("Password", pass).limit(1)
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.isEmpty)
                    {
                        db.collection("Users")
                            .whereEqualTo("Email", mail)
                            .whereEqualTo("Password", pass).limit(1)
                            .get()
                            .addOnSuccessListener { documents ->
                                if (documents.isEmpty) {
                                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                                } else {
                                    for (document in documents) {
                                        Log.d("TAG", "${document.id} => ${document.data}")
                                        CurrentData.patientUser = User(document.data["Name"].toString(),
                                            document.data["Age"].toString(),
                                            document.data["BloodGroup"].toString(),
                                            document.data["Disability"].toString(),
                                            document.data["Email"].toString(),
                                            document.data["Gender"].toString(),
                                            document.data["Height"].toString(),
                                            document.data["Password"].toString(),
                                            document.data["Phone"].toString(),
                                            document.data["Weight"].toString()
                                            )
                                        val homeIntent = Intent(this, PatientHome::class.java)
                                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(homeIntent)
                                        finish()
                                    }
                                }

                            }
                            .addOnFailureListener { exception ->
                                Log.w("TAG", "Error getting documents: ", exception)
                            }

                    }
                    else
                    {
                        for (document in documents) {
                            Log.d("TAG", "${document.id} => ${document.data}")
                            CurrentData.doctorUser = Doctor(document.data["Name"].toString(),
                                document.data["Address"].toString(),
                                document.data["Bio"].toString(),
                                document.data["Experience"].toString(),
                                document.data["Fees"].toString().toInt(),
                                document.data["Likes"].toString().toInt(),
                                document.data["Phone"].toString(),
                                document.data["ProfilePic"].toString(),
                                document.data["Rating"].toString(),
                                document.data["Speciality"].toString(),
                                document.data["TRating"].toString().toInt(),
                                document.data["Email"].toString()
                            )


                            val homeIntent = Intent(this, DoctorHome::class.java)
                            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(homeIntent)
                            finish()

                        }
                    }

                }
                .addOnFailureListener { exception ->
                    Log.w("TAG", "Error getting documents: ", exception)
                }


        }
        patient.setOnClickListener{
            intent = Intent(this, RegisterPatient::class.java)
            startActivity(intent)
        }


        doctor.setOnClickListener{
            intent = Intent(this, RegisterDoctor::class.java)
            startActivity(intent)
        }

    }
}
