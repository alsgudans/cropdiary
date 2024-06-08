package com.example.mycrodiary.My_Pages

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MypageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMypageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        val uid = currentUser?.uid.toString()

        if (uid.isNotEmpty()) {
            // Firebase 데이터베이스 참조
            val databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
                .reference
                .child("userInfo")
                .child(uid)

            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val nickname = dataSnapshot.child("nickname").getValue(String::class.java)
                    val point = dataSnapshot.child("point").getValue(String::class.java)
                    // 가져온 데이터 UI에 표시
                    binding.nickname.text = nickname.toString()
                    binding.point.text = point.toString()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("MypageActivity", "Failed to read user info", databaseError.toException())
                }
            })
        }
    }
}
