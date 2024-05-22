package com.example.mycrodiary.addcropdiarypage


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.R
import com.example.mycrodiary.cropdiaryutils.Cropinfo
import com.example.mycrodiary.databinding.ActivityAddcropdiarypageBinding


class AddcropdiarypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddcropdiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectcropData = resources.getStringArray(R.array.crop_array)
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,selectcropData)
        binding.selectCrop.adapter = spinnerAdapter

        binding.addCropButton.setOnClickListener(){

            val newItem = Cropinfo(
                binding.newCropNickname.text.toString(),
                binding.selectCrop.selectedItem.toString(),
                binding.newAddDate.text.toString()
            )
            Log.d("AddcropdiarypageActivity", "New item: $newItem")
            val resultIntent = Intent()
            resultIntent.putExtra("newItem", newItem)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}