package com.example.mycrodiary.Cropdiary_Pages

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.Database_Utils.SensorDataInfo
import com.example.mycrodiary.databinding.ActivityDiarypageBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DiarypageActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference
    private lateinit var flowTextView: TextView
    private lateinit var temperatureTextView: TextView
    private lateinit var humidityTextView: TextView
    private lateinit var illuminationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityDiarypageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .reference

        flowTextView = binding.flow
        temperatureTextView = binding.temperature
        humidityTextView = binding.humidity
        illuminationTextView = binding.illumination

        databaseReference.child("sensor").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val sensorData = dataSnapshot.getValue(SensorDataInfo::class.java)

                sensorData?.let {
                    // 데이터에서 필요한 값을 가져와서 TextView에 설정
                    flowTextView.text = "${it.weight}"
                    temperatureTextView.text = "${it.temperature}"
                    humidityTextView.text = "${it.humidity}"
                    illuminationTextView.text = "${it.illumination}"
                }
        }
            override fun onCancelled(databaseError: DatabaseError) {
                // 에러 처리
            }
        })

        binding.uploadBtn.setOnClickListener(){
            val intent = Intent(this, DailydiaryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}