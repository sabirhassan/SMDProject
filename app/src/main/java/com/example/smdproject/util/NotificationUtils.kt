/*
 * Copyright (C) 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.smdproject.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.smdproject.R
import com.example.smdproject.doctor.DoctorApointments
import com.example.smdproject.model.User
import com.example.smdproject.patient.PatientAppointments
import com.example.smdproject.receiver.DoctorRequestReceiver


// Notification ID.
private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0

// TODO: Step 1.1 extension function to send messages (GIVEN)
fun NotificationManager.sendNotificationPatient(messageBody: String, applicationContext: Context) {


    // TODO: Step 1.11 create intent
    val contentIntent = Intent(applicationContext, PatientAppointments::class.java)

    // TODO: Step 1.12 create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

// TODO: Step 2.0 add style
    // TODO: Step 1.2 get an instance of NotificationCompat.Builder
    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        // TODO: Step 1.8 use the new 'breakfast' notification channel
        applicationContext.getString(R.string.patient_notification_channel_id)
    )
        // TODO: Step 1.3 set title, text and icon to builder
        .setSmallIcon(R.drawable.health_logo)
        .setContentTitle(applicationContext.getString(R.string.patient_notification_title))
        .setContentText(messageBody)
        // TODO: Step 1.13 set content intent
        .setContentIntent(contentPendingIntent)
        // TODO: Step 2.5 set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    // TODO Step 1.4 call notify
    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.sendNotificationDoctor(patient:User ,messageBody: String, applicationContext: Context) {


    // TODO: Step 1.11 create intent
    val contentIntent = Intent(applicationContext, DoctorApointments::class.java)

    // TODO: Step 1.12 create PendingIntent
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val acceptIntent = Intent(applicationContext, DoctorRequestReceiver::class.java)
    acceptIntent.apply {
        action = "Accept"
        putExtra("Email",patient.Email)
        putExtra("Name",patient.Name)
    }
    val acceptPendingIntent = PendingIntent.getBroadcast(applicationContext,0,acceptIntent,0)


    val rejectIntent = Intent(applicationContext,DoctorRequestReceiver::class.java)
    rejectIntent .apply {
        action = "Reject"
        putExtra("Email",patient.Email)
        putExtra("Name",patient.Name)
    }
    val rejectPendingIntent = PendingIntent.getBroadcast(applicationContext,0,rejectIntent ,0)

// TODO: Step 2.0 add style

    // TODO: Step 1.2 get an instance of NotificationCompat.Builder
    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        // TODO: Step 1.8 use the new 'breakfast' notification channel
        applicationContext.getString(R.string.doctor_notification_channel_id)
    )
        // TODO: Step 1.3 set title, text and icon to builder
        .setSmallIcon(R.drawable.health_logo)
        .setContentTitle(applicationContext.getString(R.string.doctor_notification_title))
        .setContentText(messageBody)
        // TODO: Step 1.13 set content intent
        .setContentIntent(contentPendingIntent)
        // TODO: Step 2.3 add action
        .addAction(R.drawable.ic_accept_request,
            applicationContext.getString(R.string.doctor_accept),
            acceptPendingIntent)

        .addAction(R.drawable.ic_reject_request,
            applicationContext.getString(R.string.doctor_reject),
            rejectPendingIntent)
        // TODO: Step 2.5 set priority
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    // TODO Step 1.4 call notify
    // Deliver the notification
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
