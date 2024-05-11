package com.example.mycrodiary.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityMainpageBinding
import com.example.mycrodiary.pages.CropdiaryActivity
import com.example.mycrodiary.pages.MypageActivity
import com.example.mycrodiary.pages.MytreeActivity
import com.example.mycrodiary.pages.SettingActivity

class MainpageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainpageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val move_mypage = Intent(this,MypageActivity::class.java)
        val move_cropdiary = Intent(this,CropdiaryActivity::class.java)
        val move_mytree = Intent(this,MytreeActivity::class.java)
        val move_settting = Intent(this,SettingActivity::class.java)

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