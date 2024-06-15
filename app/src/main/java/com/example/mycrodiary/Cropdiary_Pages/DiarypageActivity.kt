package com.example.mycrodiary.Cropdiary_Pages

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.Database_Utils.InputDataInfo
import com.example.mycrodiary.Database_Utils.SensorDataInfo
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityDiarypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DiarypageActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var flowTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var illuminationTextView: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var nickname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Firebase 초기화
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid.toString()

        nickname = intent.getStringExtra("nickname").toString()

        // 스피너 어댑터 설정
        val selectdayData = resources.getStringArray(R.array.day_array)
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, selectdayData)
        binding.daySpinner.adapter = spinnerAdapter

        // 데이터베이스 참조 설정
        databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app").reference
        val btndatabaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("cropInfo")
            .child(uid)
            .child(nickname)

        // 텍스트뷰 연결
        flowTextView = binding.flow
        temperatureTextView = binding.temperature
        humidityTextView = binding.humidity
        illuminationTextView = binding.illumination

        // 센서 값 데이터베이스에서 읽어오기
        databaseReference.child("sensor").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val sensorData = dataSnapshot.getValue(SensorDataInfo::class.java)
                sensorData?.let {
                    flowTextView.text = "${it.weight}kg"
                    temperatureTextView.text = "${it.temperature}C"
                    humidityTextView.text = "${it.humidity}%"
                    illuminationTextView.text = "${it.illumination}lux"
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
            }
        })

        // 닉네임 받아오기

        // 업로드 버튼 클릭 리스너
        binding.uploadBtn.setOnClickListener {
            val selecteddayText = binding.daySpinner.selectedItem.toString()
            val flowText = flowTextView.text.toString()
            val temperatureText = temperatureTextView.text.toString()
            val humidityText = humidityTextView.text.toString()
            val illuminationText = illuminationTextView.text.toString()

            // 데이터베이스 경로 설정
            val buttonDataRef = btndatabaseReference.child(selecteddayText)
            val motorcontrol0 = databaseReference.child("motorControl")
            val pluspoint = databaseReference.child("userInfo").child(uid).child("point")

            // 라디오 버튼 선택 값 가져오기
            val leafStatus = getRadioGroupValue(binding.leafgroup)
            val flowerStatus = getRadioGroupValue(binding.flowergroup)
            val bugStatus = getRadioGroupValue(binding.buggroup)
            val plantStatus = getRadioGroupValue(binding.plantgroup)

            // 센서 데이터 저장
            val newData = InputDataInfo(
                day = selecteddayText,
                group1 = leafStatus,
                group2 = flowerStatus,
                group3 = bugStatus,
                group4 = plantStatus,
                weight = flowText,
                temperature = temperatureText,
                humidity = humidityText,
                illumination = illuminationText
            )
            buttonDataRef.setValue(newData)
            motorcontrol0.setValue(0)

            // 현재 포인트를 가져와서 200포인트 증가
            pluspoint.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val currentPoint = dataSnapshot.getValue(String::class.java)?.toInt() ?: 0
                    val updatedPoint = currentPoint + 200
                    pluspoint.setValue(updatedPoint.toString())
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // 에러 처리
                }
            })

            finish()
        }
    }

    private fun getRadioGroupValue(radioGroup: RadioGroup): Int {
        val selectedId = radioGroup.checkedRadioButtonId
        if (selectedId == -1) return 0 // 기본값으로 0 반환
        val radioButton = findViewById<RadioButton>(selectedId)
        return radioButton.tag.toString().toInt()
    }
}
