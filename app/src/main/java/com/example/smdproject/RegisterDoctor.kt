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
import kotlinx.android.synthetic.main.activity_register_doctor.*

class RegisterDoctor : AppCompatActivity() {


    lateinit var experience: EditText
    lateinit var doctorbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_doctor)
        val db = Firebase.firestore

        experience = findViewById(R.id.experience)


        doctorbtn=findViewById(R.id.registerasDoc)
        doctorbtn.setOnClickListener() {
            val name = username.text.toString().trim()
            val email = Email.text.toString().trim()
            val address = Address.text.toString().trim()
            val phone = Phone.text.toString().trim()
            val pass = Password.text.toString().trim()
            val bio = Bio.text.toString().trim()
            val exp = experience.text.toString().trim()
            val speciality = Speciality.text.toString().trim()


            if (name.isEmpty()) {
                username.error = "Name required"
                username.requestFocus()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                Password.error = "Password required"
                Password.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                Email.error = "Email required"
                Email.requestFocus()
                return@setOnClickListener
            }
            if (address.isEmpty()) {
                Address.error = "Address required"
                Address.requestFocus()
                return@setOnClickListener
            }
            if (bio.isEmpty()) {
                Bio.error = "Bio required"
                Bio.requestFocus()
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                Phone.error = "Phone required"
                Phone.requestFocus()
                return@setOnClickListener
            }
            if (exp.isEmpty()) {
                experience.error = "Experience required"
                experience.requestFocus()
                return@setOnClickListener
            }
            if (speciality.isEmpty()) {
                Speciality.error = "Speciality required"
                Speciality.requestFocus()
                return@setOnClickListener
            }

            db.collection("Doctors")
                .whereEqualTo("Email", email)
                .get()
                .addOnSuccessListener { documents ->
                    if(documents.isEmpty)
                    {
                        val data1 = hashMapOf(
                            "Name" to name,
                            "Bio" to bio,
                            "Address" to address,
                            "Phone" to phone,
                            "Password" to pass,
                            "Experience" to exp,
                            "Fees" to 0,
                            "Likes" to 0,
                            "Rating" to 0,
                            "TRating" to 0,
                            "Email" to email,
                            "ProfilePic" to "",
                            "Speciality" to speciality

                        )
                        db.collection("Doctors")
                            .add(data1)
                            .addOnSuccessListener { documentReference ->
                                Log.d("tag1", "DocumentSnapshot written with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w("tag2", "Error adding document", e)
                            }

                        Toast.makeText(applicationContext,"Successfully Registered! Login to Continue",
                            Toast.LENGTH_SHORT).show()
                        intent = Intent(this, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
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
