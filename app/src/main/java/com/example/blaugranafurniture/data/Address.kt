package com.example.blaugranafurniture.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val addressTitle:String,
    val fullName:String,
    val email:String,
    val phone:String,
    val city:String,
    val zipCode:String,
): Parcelable{
    constructor():this("","","","","","")
}
