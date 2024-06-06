package com.example.mycrodiary.Cropdiary_Pages

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.Cropdiary_Utils.Adapter
import com.example.mycrodiary.Cropdiary_Utils.DiaryAdapter
import com.example.mycrodiary.Database_Utils.Cropinfo
import com.example.mycrodiary.Database_Utils.SensorDataInfo
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityDailydiaryBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DailydiaryActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var binding: ActivityDailydiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailydiaryBinding.inflate(layoutInflater) // 뷰바인딩 초기화
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid.toString()

        val cropName = intent.getStringExtra("cropname")
        val nickname = intent.getStringExtra("nickname").toString()
        val date = intent.getStringExtra("date")

        val cropNameTextView = findViewById<TextView>(R.id.crop_name)
        val nicknameTextView = findViewById<TextView>(R.id.crop_nickname)
        val dateTextView = findViewById<TextView>(R.id.add_date)

        cropNameTextView.text = cropName
        nicknameTextView.text = nickname
        dateTextView.text = date

        val databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("cropInfo")
            .child(uid)
            .child(nickname)
        val addedDiaryList = ArrayList<SensorDataInfo>()
        val adapter = DiaryAdapter(this, addedDiaryList)
        binding.addedDiaryList.adapter = adapter

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                addedDiaryList.clear() // 리스트 초기화
                for (snapshot in dataSnapshot.children) {


                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("error count","Data upload Error")
            }
        })

        binding.addDiaryBtn.setOnClickListener(){
            val intent = Intent(this,DiarypageActivity::class.java)
            intent.putExtra("cropname", cropName)
            intent.putExtra("nickname", nickname)
            intent.putExtra("date", date)
            startActivity(intent)
            finish()
        }





    }
}