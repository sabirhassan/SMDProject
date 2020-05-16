package com.example.smdproject.patient

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

import com.example.smdproject.R
import com.example.smdproject.model.Doctor
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging

class PatientAppointments : Fragment() {

    private val TOPIC = "Doctor Request Status"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_appointments, container, false)
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH).apply { setShowBadge(false) }

            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.patient_notification_channel_description)

            val notificationManager = requireActivity().getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
            .addOnCompleteListener { task ->
                var message = getString(R.string.message_subscribed)
                if (!task.isSuccessful) {
                    message = getString(R.string.message_subscribe_failed)
                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
    }

}
