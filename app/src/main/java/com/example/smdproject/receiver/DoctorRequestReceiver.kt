package com.example.smdproject.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class DoctorRequestReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val action = intent?.action

        if (action.equals("Accept")){
            val ussreID = intent?.getStringExtra("Email")
            Toast.makeText(context,"Accepted ID: $ussreID",Toast.LENGTH_SHORT).show()
            val name = intent?.getStringExtra("Name")
            Toast.makeText(context,"Rejected ID: $ussreID",Toast.LENGTH_SHORT).show()
        }

        if (action.equals("Reject")){
            val ussreID = intent?.getStringExtra("Email")
            val name = intent?.getStringExtra("Name")
            Toast.makeText(context,"Rejected ID: $ussreID",Toast.LENGTH_SHORT).show()
        }
    }
}