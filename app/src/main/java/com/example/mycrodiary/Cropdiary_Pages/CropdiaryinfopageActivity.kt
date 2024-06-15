package com.example.mycrodiary.Cropdiary_Pages

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityCropdiaryinfopageBinding
import com.example.mycrodiary.databinding.ActivityCropdiarypageBinding

class CropdiaryinfopageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCropdiaryinfopageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropdiaryinfopageBinding.inflate(layoutInflater)
        setContentView(binding.root)



    }
}