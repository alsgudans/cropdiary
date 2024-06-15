package com.example.mycrodiary.Setting_Pages

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityChangepasswordBinding
import com.example.mycrodiary.databinding.ActivitySettingpageBinding

class SettingpageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySettingpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.passwordChange.setOnClickListener(){
            val intentPW = Intent(this, ChangepasswordActivity::class.java)
            startActivity(intentPW)
        }

        binding.nicknameChange.setOnClickListener(){
            val intentNICK = Intent(this, ChangenicknameActivity::class.java)
            startActivity(intentNICK)
        }

    }
}