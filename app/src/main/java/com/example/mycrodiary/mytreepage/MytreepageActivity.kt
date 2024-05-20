package com.example.mycrodiary.mytreepage

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityMytreepageBinding

class MytreepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMytreepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}