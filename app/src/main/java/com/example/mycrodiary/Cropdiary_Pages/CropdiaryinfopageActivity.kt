package com.example.mycrodiary.Cropdiary_Pages

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityCropdiaryinfopageBinding

class CropdiaryinfopageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCropdiaryinfopageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}