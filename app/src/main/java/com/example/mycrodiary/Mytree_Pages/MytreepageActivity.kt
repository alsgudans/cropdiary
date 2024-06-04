package com.example.mycrodiary.Mytree_Pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityMytreepageBinding

class MytreepageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMytreepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}