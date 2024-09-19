package com.example.mycrodiary.Cropdiary_Pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycrodiary.Cropdiary_Utils.DiaryAdapter
import com.example.mycrodiary.Database_Utils.InputDataInfo
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityCropDiaryManagementPageBinding
import com.example.mycrodiary.databinding.ActivityDailydiaryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.text.SimpleDateFormat
import java.util.*

class CropDiaryManagementPageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCropDiaryManagementPageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var lastAddDateRef: DatabaseReference
    private var isButtonEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCropDiaryManagementPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid.toString()

        val addedDiaryList = ArrayList<InputDataInfo>()
        val adapter = DiaryAdapter(this, addedDiaryList)
        binding.addedDiaryList.adapter = adapter

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

        databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("cropInfo")
            .child(uid)
            .child("nickname")

        val databaseReference0 = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app").reference
        lastAddDateRef = databaseReference.child("last_add_date")

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
                            day = day,
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

        binding.addedDiaryList.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = addedDiaryList[position]
            val intent = Intent(this, CropdiaryinfopageActivity::class.java)
            intent.putExtra("day", selectedItem.day) // day 값만 전달
            intent.putExtra("nickname", nickname)
            startActivity(intent)
        }

        lastAddDateRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastDate = snapshot.getValue(String::class.java)
                val currentDate = getCurrentDate()

                isButtonEnabled = lastDate != currentDate
                binding.addCropDiaryBtn.isEnabled = isButtonEnabled
            }

            override fun onCancelled(error: DatabaseError) {
                // 에러 처리
                Log.d("Data Error", "Failed to read last add date", error.toException())
            }
        })
    }
    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date())
    }
}