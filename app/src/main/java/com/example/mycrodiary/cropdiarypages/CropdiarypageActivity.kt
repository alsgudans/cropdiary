package com.example.mycrodiary.cropdiarypages

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.addcropdiarypage.AddcropdiarypageActivity
import com.example.mycrodiary.cropdiaryutils.Adapter
import com.example.mycrodiary.cropdiaryutils.Cropinfo
import com.example.mycrodiary.databinding.ActivityCropdiarypageBinding

@Suppress("DEPRECATION")
class CropdiarypageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityCropdiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, AddcropdiarypageActivity::class.java)

        binding.addDiaryBtn.setOnClickListener(){
            startActivity(intent)
        }


    }
}

