package com.example.mycrodiary.cropdiarypages


import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.addcropdiarypage.AddcropdiarypageActivity
import com.example.mycrodiary.cropdiaryutils.Adapter

import com.example.mycrodiary.cropdiaryutils.Cropinfo
import com.example.mycrodiary.databinding.ActivityCropdiarypageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class CropdiarypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCropdiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("cropInfo")

        val cropList = ArrayList<Cropinfo>()
        val adapter = Adapter(this, cropList)
        binding.cropDiaryListview.adapter = adapter

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d("count", "onDataChange called")
                for (snapshot in dataSnapshot.children) {
                    val name = snapshot.child("name").getValue(String::class.java)
                    val nickname = snapshot.child("nickname").getValue(String::class.java)
                    val date = snapshot.child("date").getValue(String::class.java)
                    val cropinfo = Cropinfo(name, nickname, date)
                    cropList.add(cropinfo)

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("error count","Data upload Error")
            }
        })

        binding.addDiaryBtn.setOnClickListener(){
            val intent = Intent(this, AddcropdiarypageActivity::class.java)
            startActivity(intent)
        }


    }
}

