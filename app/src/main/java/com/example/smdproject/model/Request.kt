package com.example.smdproject.model

import java.io.Serializable

class Request: Serializable
{
    var DoctorName:String?=null
    var DoctorID:String?=null
    var PatientID:String?=null
    var Date:String?=null
    var Status:String?=null


    constructor()
    {

    }

    constructor(
        DoctorName: String?,
        DoctorID: String?,
        PatientID: String?,
        Date: String?,
        Status: String?
    ) {
        this.DoctorName = DoctorName
        this.DoctorID = DoctorID
        this.PatientID = PatientID
        this.Date = Date
        this.Status = Status
    }


}