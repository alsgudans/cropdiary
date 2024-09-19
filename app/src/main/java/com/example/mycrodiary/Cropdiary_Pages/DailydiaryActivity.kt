package com.example.mycrodiary.Cropdiary_Pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.Cropdiary_Utils.DiaryAdapter
import com.example.mycrodiary.Database_Utils.InputDataInfo
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityDailydiaryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*

class DailydiaryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDailydiaryBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var lastAddDateRef: DatabaseReference
    private var isButtonEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 엑티비티 바인딩
        binding = ActivityDailydiaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 유저 정보를 활용하기 위한 정보 라이브러리 및 정보 저장
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid.toString()

        // 각 변수에 Cropdiarypage엑티비티의 item에서 putextra한 텍스트를 저장
        val cropName = intent.getStringExtra("cropname")
        val nickname = intent.getStringExtra("nickname").toString()
        val date = intent.getStringExtra("date").toString()

        // 각 변수에 Dailydiary엑티비티의 텍스트뷰를 지정
        val cropNameTextView = findViewById<TextView>(R.id.crop_name)
        val nicknameTextView = findViewById<TextView>(R.id.crop_nickname)
        val dateTextView = findViewById<TextView>(R.id.day)

        val daysPassed = calculateDaysPassed(date)


        // 지정된 Dailydiary엑티비티의 텍스트뷰에, Cropdiarypage엑티비티의 item에서 가져온 텍스트를 저장
        cropNameTextView.text = cropName
        nicknameTextView.text = nickname
        dateTextView.text = "${daysPassed}일차"

        // Firebase 인증 및 데이터베이스 참조: cropInfo-uid-nickname 아래 있는 정보들
        databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("sensor")


        val databaseReference0 = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("cropInfo")
            .child(uid)
            .child(nickname)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val temperature = dataSnapshot.child("temperature").getValue(Double::class.java)
                val humidity = dataSnapshot.child("humidity").getValue(Double::class.java)
                // 가져온 데이터 UI에 표시
                binding.realTimeTemperature.text = temperature.toString()
                binding.realTimeHumidity.text = humidity.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("MypageActivity", "Failed to read user info", databaseError.toException())
            }
        })

        // 작물 일지를 작성한 정보를 Firebase Realtime Database에서 가져와서 listview에 추가

        // 마지막 일지 추가 날짜를 확인하여 버튼을 활성화/비활성화

        binding.LEDSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // 스위치가 켜진 경우
                Toast.makeText(this, "LED가 켜졌습니다.", Toast.LENGTH_SHORT).show()
                // Firebase에 상태 저장 및 동작 수행
                databaseReference0.child("ledControl").setValue(1)
            } else {
                // 스위치가 꺼진 경우
                Toast.makeText(this, "LED가 꺼졌습니다.", Toast.LENGTH_SHORT).show()
                // Firebase에 상태 저장 및 동작 수행
                databaseReference0.child("ledControl").setValue(0)
            }
        }
    }
    // 현재 날짜를 "yyyy-MM-dd" 형식으로 반환
    private fun calculateDaysPassed(createdDate: String): Int {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(createdDate)
        val currentDate = Date()
        val diff = currentDate.time - date.time
        return ((diff / (1000 * 60 * 60 * 24))+1).toInt()
    }
}
