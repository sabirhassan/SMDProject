package com.example.smdproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.smdproject.doctor.DoctorHome
import com.example.smdproject.patient.PatientHome
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var patient: TextView
    lateinit var doctor: TextView
    lateinit var loginbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        patient=findViewById(R.id.patient_reg)
        doctor=findViewById(R.id.doctor_reg)
        loginbtn = findViewById(R.id.loginbtn)

        loginbtn.setOnClickListener{
            val mail = user.text.toString()
            if(mail.equals("asd"))
            {
                intent = Intent(this, DoctorHome::class.java)
                startActivity(intent)
            }
            else
            {
                intent = Intent(this, PatientHome::class.java)
                startActivity(intent)
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
