package com.example.mycrodiary.Cropdiary_Pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityCropdiaryinfopageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CropdiaryinfopageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCropdiaryinfopageBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCropdiaryinfopageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid.toString()

        // Intent로 전달된 데이터 받기
        val day = intent.getStringExtra("day")

        // 데이터베이스 참조 설정
        databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .reference.child("cropInfo")
            .child(uid)
            .child("test tomato")
            .child(day ?: "")

        // 데이터베이스에서 데이터 불러오기
        databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val day = dataSnapshot.child("day").getValue(String::class.java)
                val weight = dataSnapshot.child("weight").getValue(Double::class.java) ?: 0.0
                val temperature = dataSnapshot.child("temperature").getValue(Double::class.java) ?: 0.0
                val humidity = dataSnapshot.child("humidity").getValue(Double::class.java) ?: 0.0
                val illumination = dataSnapshot.child("illumination").getValue(Double::class.java) ?: 0.0
                val group1 = dataSnapshot.child("group1").getValue(Int::class.java) ?: 0
                val group2 = dataSnapshot.child("group2").getValue(Int::class.java) ?: 0
                val group3 = dataSnapshot.child("group3").getValue(Int::class.java) ?: 0
                val group4 = dataSnapshot.child("group4").getValue(Int::class.java) ?: 0

                // 텍스트뷰에 데이터 설정
                binding.day.text = "일지: $day"
                binding.flow.text = "희석액 공급량: $weight"
                binding.temperature.text = "온도: $temperature"
                binding.humidity.text = "습도: $humidity"
                binding.illumination.text = "조도: $illumination"
                binding.leafStatus.text = "잎 상태: $group1"
                binding.flowerStatus.text = "꽃 상태: $group2"
                binding.bugStatus.text = "병충해 상태: $group3"
                binding.fruitStatus.text = "열매 상태: $group4"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
            }
        })
    }
}
