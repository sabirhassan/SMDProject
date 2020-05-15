package com.example.smdproject.model

import java.io.Serializable

class Doctor: Serializable
{
    var Name:String?=null
    var Address:String?=null
    var Bio:String?=null
    var Experience:Int?=null
    var Fees:Int?=null
    var Likes:Int?=null
    var Phone:String?=null
    var ProfilePic:String?=null
    var Rating: String?=null
    var Speciality:String?=null
    var TRating:Int?=null
    var Email:String?=null

    constructor()
    {

    }

    constructor(
        Name: String?,
        Address: String?,
        Bio: String?,
        Experience: Int?,
        Fees: Int?,
        Likes: Int?,
        Phone: String?,
        ProfilePic: String?,
        Rating: String?,
        Speciality: String?,
        TRating: Int?,
        Email: String?
    ) {
        this.Name = Name
        this.Address = Address
        this.Bio = Bio
        this.Experience = Experience
        this.Fees = Fees
        this.Likes = Likes
        this.Phone = Phone
        this.ProfilePic = ProfilePic
        this.Rating = Rating
        this.Speciality = Speciality
        this.TRating = TRating
        this.Email=Email
    }


}