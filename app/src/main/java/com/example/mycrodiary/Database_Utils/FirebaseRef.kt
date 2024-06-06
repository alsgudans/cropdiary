package com.example.mycrodiary.Database_Utils

import com.google.firebase.Firebase
import com.google.firebase.database.database


class FirebaseRef {
    companion object {
        private val database = Firebase.database("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
        val userInfo = database.getReference("userInfo")
        val cropInfo = database.getReference("cropInfo")
        val getsensorInfo = database.getReference("sensor")
    }
}