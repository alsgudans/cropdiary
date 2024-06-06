package com.example.mycrodiary.Cropdiary_Pages


import android.content.Intent
import android.os.Bundle
import android.util.Log

import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.Add_Cropdiary_Pages.AddcropdiarypageActivity
import com.example.mycrodiary.Cropdiary_Utils.Adapter

import com.example.mycrodiary.Database_Utils.Cropinfo
import com.example.mycrodiary.databinding.ActivityCropdiarypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CropdiarypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityCropdiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 인증 및 데이터베이스 참조
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        val databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("cropInfo")
            .child(uid)
        val cropList = ArrayList<Cropinfo>()
        val adapter = Adapter(this, cropList)
        binding.cropDiaryListview.adapter = adapter

        // 작물 정보를 Firebase Realtime Database에서 가져오기
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                cropList.clear() // 리스트 초기화
                for (snapshot in dataSnapshot.children) {
                    val cropName = snapshot.child("cropname").getValue(String::class.java)
                    val nickname = snapshot.child("nickname").getValue(String::class.java)
                    val date = snapshot.child("date").getValue(String::class.java)
                    if (cropName != null && nickname != null && date != null) {
                        val cropInfo = Cropinfo(cropName, nickname, date)
                        cropList.add(cropInfo)
                    }
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

        binding.cropDiaryListview.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = cropList[position]
            val intent = Intent(this, DailydiaryActivity::class.java)

            intent.putExtra("cropname",selectedItem.cropname)
            intent.putExtra("nickname",selectedItem.nickname)
            intent.putExtra("date", selectedItem.date)

            startActivity(intent)
        }
    }
}


