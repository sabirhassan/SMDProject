package com.example.smdproject.patient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.smdproject.R
import com.google.android.material.navigation.NavigationView

class PatientHome : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    private val fragmentManager = supportFragmentManager

    private val profileFragment = PatientProfile()
    private val requestFragment = PatientRequests()
    private val appointmentFragment = PatientAppointments()
    private val pendingFragment = PatientPendings()
    private val prescriptionFragment = PatientPrescriptions()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patient_home)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        /* Display profile Fragment initially */
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.myFragment, prescriptionFragment)
        fragmentTransaction.commit()


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_patient_profile -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, profileFragment)
                fragmentTransaction.commit()

            }
            R.id.nav_patient_appointments -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, appointmentFragment)
                fragmentTransaction.commit()
            }
            R.id.nav_patient_requests -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, requestFragment)
                fragmentTransaction.commit()

            }
            R.id.nav_patient_pending -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, pendingFragment)
                fragmentTransaction.commit()
            }
            R.id.nav_patient_prescription -> {
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.myFragment, prescriptionFragment)
                fragmentTransaction.commit()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
