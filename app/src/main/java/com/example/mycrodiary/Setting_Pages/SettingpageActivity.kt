package com.example.mycrodiary.Setting_Pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivitySettingpageBinding

class SettingpageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivitySettingpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}