package com.example.mycrodiary.mypage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityMypageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class MypageActivity : AppCompatActivity() {

    val user = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mypage)
        val binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val user = Firebase.auth.currentUser


    }
}