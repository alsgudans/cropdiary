package com.example.mycrodiary.cropdiarypages

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.R

class CropDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop_detail)

        val cropName = intent.getStringExtra("cropname")
        val nickname = intent.getStringExtra("nickname")
        val date = intent.getStringExtra("date")

        val cropNameTextView = findViewById<TextView>(R.id.crop_name_detail)
        val nicknameTextView = findViewById<TextView>(R.id.nickname_detail)
        val dateTextView = findViewById<TextView>(R.id.date_detail)

        cropNameTextView.text = cropName
        nicknameTextView.text = nickname
        dateTextView.text = date
    }
}
