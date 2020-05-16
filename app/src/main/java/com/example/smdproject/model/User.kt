package com.example.smdproject.model

import java.io.Serializable

class User: Serializable
{
    var Name:String?=null
    var Age:String?=null
    var BloodGroup:String?=null
    var Disability:String?=null
    var Email:String?=null
    var Gender:String?=null
    var Height: String?=null
    var Password:String?=null
    var Phone:String?=null
    var Weight:String?=null

    constructor()
    {

    }

    constructor(
        Name: String?,
        Age: String?,
        BloodGroup: String?,
        Disability: String?,
        Email: String?,
        Gender: String?,
        Height: String?,
        Password: String?,
        Phone: String?,
        Weight: String?
    ) {
        this.Name = Name
        this.Age = Age
        this.BloodGroup = BloodGroup
        this.Disability = Disability
        this.Email = Email
        this.Gender = Gender
        this.Phone = Phone
        this.Height = Height
        this.Password = Password
        this.Weight = Weight
    }

}