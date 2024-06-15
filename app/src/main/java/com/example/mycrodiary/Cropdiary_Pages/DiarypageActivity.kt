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

            // 센서 데이터 저장
            val newData = InputDataInfo(
                day = selecteddayText,
                group1 = getRadioGroupValue(binding.leafgroup),
                group2 = getRadioGroupValue(binding.flowergroup),
                group3 = getRadioGroupValue(binding.buggroup),
                group4 = getRadioGroupValue(binding.plantgroup),
                weight = "${flowText}kg",
                temperature = "${temperatureText}C",
                humidity = "${humidityText}%",
                illumination = "${illuminationText}lux"
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

        // 라디오 그룹 리스너 설정
        setRadioGroupListener(binding.leafgroup, "leaf_status", uid)
        setRadioGroupListener(binding.flowergroup, "flower_status", uid)
        setRadioGroupListener(binding.buggroup, "bug_status", uid)
        setRadioGroupListener(binding.plantgroup, "plant_status", uid)
    }

    private fun setRadioGroupListener(radioGroup: RadioGroup, fieldName: String, uid: String) {
        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val radioButton = findViewById<RadioButton>(checkedId)
            val selectedValue = radioButton.tag.toString().toInt()
            saveRadioSelectionToFirebase(uid, fieldName, selectedValue)
        }
    }

    private fun saveRadioSelectionToFirebase(uid: String, fieldName: String, value: Int) {
        val userRef = databaseReference.child("cropInfo").child(uid)
        userRef.child(fieldName).setValue(value).addOnCompleteListener {
            if (it.isSuccessful) {

            } else {

            }
        }
    }

    private fun getRadioGroupValue(radioGroup: RadioGroup): Int {
        val selectedId = radioGroup.checkedRadioButtonId
        if (selectedId == -1) return 0 // 기본값으로 0 반환
        val radioButton = findViewById<RadioButton>(selectedId)
        return radioButton.tag.toString().toInt()
    }
}
