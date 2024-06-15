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

class DailydiaryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityDailydiaryBinding

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
        val date = intent.getStringExtra("date")

        // 각 변수에 Dailydiary엑티비티의 텍스트뷰를 지정
        val cropNameTextView = findViewById<TextView>(R.id.crop_name)
        val nicknameTextView = findViewById<TextView>(R.id.crop_nickname)
        val dateTextView = findViewById<TextView>(R.id.add_date)

        // 지정된 Dailydiary엑티비티의 텍스트뷰에, Cropdiarypage엑티비티의 item에서 가져온 텍스트를 저장
        cropNameTextView.text = cropName
        nicknameTextView.text = nickname
        dateTextView.text = date

        // Firebase 인증 및 데이터베이스 참조: cropInfo-uid-nickname 아래 있는 정보들
        val databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("cropInfo")
            .child(uid)
            .child(nickname)

        val databaseReference0 = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app").reference

        val addedDiaryList = ArrayList<InputDataInfo>()
        val adapter = DiaryAdapter(this, addedDiaryList)
        binding.addedDiaryList.adapter = adapter

        // 작물 일지를 작성한 정보를 Firebase Realtime Database에서 가져와서 listview에 추가
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                addedDiaryList.clear() // 리스트 초기화
                for (snapshot in dataSnapshot.children) {
                    val day = snapshot.child("day").getValue(String::class.java)
                    val weight = snapshot.child("weight").getValue(String::class.java)
                    val temperature = snapshot.child("temperature").getValue(String::class.java)
                    val humidity = snapshot.child("humidity").getValue(String::class.java)
                    val illumination = snapshot.child("illumination").getValue(String::class.java)

                    if (day != null && weight != null && temperature != null && humidity != null && illumination != null) {
                        val inputInfo = InputDataInfo(
                            day = "${day} day",
                            weight = weight,
                            temperature = temperature,
                            humidity = humidity,
                            illumination = illumination
                        )
                        addedDiaryList.add(inputInfo)
                    } else {
                        Log.d("Data Check", "Some values are null, skipping this entry.")
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("error count","Data upload Error")
            }
        })

        // 리스트뷰 아이템 클릭 리스너 설정
        binding.addedDiaryList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = addedDiaryList[position]
            val intent = Intent(this, CropdiaryinfopageActivity::class.java)
            intent.putExtra("day", selectedItem.day) // day 값만 전달
            intent.putExtra("nickname", nickname)
            startActivity(intent)
        }

        binding.addDiaryBtn.setOnClickListener {
            val addbtnDataRef = databaseReference0.child("motorControl")
            addbtnDataRef.setValue(1)

            val intent = Intent(this, DiarypageActivity::class.java)
            intent.putExtra("cropname", cropName)
            intent.putExtra("nickname", nickname)
            intent.putExtra("date", date)
            startActivity(intent)
            finish()
        }

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
}
