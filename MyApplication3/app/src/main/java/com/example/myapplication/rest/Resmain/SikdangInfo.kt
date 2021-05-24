package com.example.myapplication.rest.Resmain

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class SikdangInfo(
        val Store_image : String? = "",
        val phone_number : String? = "",
        val store_id : String?="",
        val store_name : String? = "",
        val store_type : String? = ""

){
    fun toMap(): Map<String, Any?> {
        return mapOf(
                "Store_image" to Store_image,
                "phone_number" to phone_number,
                "store_id" to store_id,
                "store_name" to store_name,
                "store_type" to store_type
        )
    }
}