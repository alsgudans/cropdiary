package com.example.mycrodiary.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityMainpageBinding
import com.example.mycrodiary.menu.MypageActivity

class MainpageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val move_mypage = Intent(this,MypageActivity::class.java)

        binding.myPage.setOnClickListener(){
            startActivity(move_mypage)
        }

    }
}