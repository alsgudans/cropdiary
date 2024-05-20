package com.example.mycrodiary.cropdiarypages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityCropdiaryBinding
import com.example.mycrodiary.editcroplist.Adapter
import com.example.mycrodiary.editcroplist.Cropinfo

class CropdiaryActivity : AppCompatActivity() {

    var croplist = arrayListOf<Cropinfo>(
        Cropinfo("tomato","토마토","tomato","2024.05.20")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCropdiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cropdiaryAdapter = Adapter(this, croplist)
        binding.cropDiaryListview.adapter = cropdiaryAdapter

    }
}