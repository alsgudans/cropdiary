package com.example.mycrodiary.addcropdiarypage


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.R
import com.example.mycrodiary.accountutil.FirebaseRef
import com.example.mycrodiary.cropdiarypages.CropdiarypageActivity
import com.example.mycrodiary.cropdiaryutils.Adapter
import com.example.mycrodiary.cropdiaryutils.Cropinfo
import com.example.mycrodiary.databinding.ActivityAddcropdiarypageBinding
import com.example.mycrodiary.databinding.ActivityCropdiarypageBinding
import com.google.firebase.database.FirebaseDatabase


class AddcropdiarypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAddcropdiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectcropData = resources.getStringArray(R.array.crop_array)
        val spinnerAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,selectcropData)
        binding.selectCrop.adapter = spinnerAdapter

        val intent = Intent(this, ActivityCropdiarypageBinding::class.java)

        binding.addCropButton.setOnClickListener(){
            val new_crop_name= binding.selectCrop.selectedItem.toString()
            val new_crop_nickname = binding.newCropNickname.text.toString()
            val new_add_date = binding.newAddDate.text.toString()

            val take_cropinfo = Cropinfo(new_crop_name,new_crop_nickname,new_add_date)
            FirebaseRef.cropInfo.child(new_crop_nickname).setValue(take_cropinfo)

        }
    }
}