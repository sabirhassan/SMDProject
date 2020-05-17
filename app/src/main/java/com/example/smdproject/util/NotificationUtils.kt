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


fun NotificationManager.sendNotificationPatient(messageBody: String, applicationContext: Context) {



    val contentIntent = Intent(applicationContext, PatientAppointments::class.java)
    val contentPendingIntent = PendingIntent.getActivity(
        applicationContext,
        NOTIFICATION_ID,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )


    // Build the notification
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.patient_notification_channel_id)
    )
        .setSmallIcon(R.drawable.health_logo)
        .setContentTitle(applicationContext.getString(R.string.patient_notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.sendNotificationDoctor(patient:User ,messageBody: String, applicationContext: Context) {


    val contentIntent = Intent(applicationContext, DoctorApointments::class.java)
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

    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.doctor_notification_channel_id)
    )
        .setSmallIcon(R.drawable.health_logo)
        .setContentTitle(applicationContext.getString(R.string.doctor_notification_title))
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .addAction(R.drawable.ic_accept_request_foreground,
            applicationContext.getString(R.string.doctor_accept),
            acceptPendingIntent)

        .addAction(R.drawable.ic_reject_request_foreground,
            applicationContext.getString(R.string.doctor_reject),
            rejectPendingIntent)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)
    notify(NOTIFICATION_ID, builder.build())
}

fun NotificationManager.cancelNotifications() {
    cancelAll()
}
