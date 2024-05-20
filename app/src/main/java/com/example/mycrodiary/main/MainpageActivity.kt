package com.example.mycrodiary.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityMainpageBinding
import com.example.mycrodiary.cropdiarypages.CropdiaryActivity
import com.example.mycrodiary.mypage.MypageActivity
import com.example.mycrodiary.mytreepage.MytreeActivity
import com.example.mycrodiary.settingpage.SettingActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainpageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        auth = Firebase.auth

        val binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val move_mypage = Intent(this, MypageActivity::class.java)
        val move_cropdiary = Intent(this, CropdiaryActivity::class.java)
        val move_mytree = Intent(this,MytreeActivity::class.java)
        val move_settting = Intent(this, SettingActivity::class.java)
        val user = auth.currentUser

        binding.myPage.setOnClickListener(){
            startActivity(move_mypage)
        }
        binding.cropDiary.setOnClickListener(){
            startActivity(move_cropdiary)
        }
        binding.myTree.setOnClickListener(){
            startActivity(move_mytree)
        }
        binding.setting.setOnClickListener(){
            startActivity(move_settting)
        }

    }
}