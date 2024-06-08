package com.example.mycrodiary.Setting_Pages

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.Database_Utils.FirebaseRef
import com.example.mycrodiary.databinding.ActivityChangenicknameBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase

class ChangenicknameActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩
        val binding = ActivityChangenicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 로그인 사용자 정보 불러오기 위한 라이브러리
        auth = Firebase.auth

        // 로그인한 사용자 uid가져오기
        val user = auth.currentUser
        val uid = user?.uid.toString()



        val databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app").reference

        // firebase 경로 지정해서 닉네임 정보 변경.
        binding.changeNicknmaeBtn.setOnClickListener(){
            // 변경할 닉네임 스트링을 받아 저장
            val newNickname = binding.changeNickname.text.toString()

            databaseReference.child("userInfo").child(uid).child("nickname").setValue(newNickname)
        }


    }
}