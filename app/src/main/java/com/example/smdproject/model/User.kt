package com.example.smdproject.model

import java.io.Serializable

data class User(var Name: String?,
                var Age: String?,
                var BloodGroup: String?,
                var Disability: String?,
                var Email: String?,
                var Gender: String?,
                var Height: String?,
                var Password: String?,
                var Phone: String?,
                var Weight: String?): Serializable
