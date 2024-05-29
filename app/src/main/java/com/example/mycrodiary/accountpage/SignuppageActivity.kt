package com.example.mycrodiary.accountpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.accountutil.FirebaseRef
import com.example.mycrodiary.accountutil.UserInfo
import com.example.mycrodiary.databinding.ActivitySignuppageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase

class SignuppageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySignuppageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val intent = Intent(this, LoginpageActivity::class.java)

        binding.makeAccount.setOnClickListener {
            val newEmail = binding.newEmail.text.toString()
            val newPw = binding.newPw.text.toString()
            val nickname = binding.newNickname.text.toString()
            val checkNewPw = binding.checkNewPw.text.toString()

            if (newEmail.isBlank()) {
                Toast.makeText(this, "Email을 입력하세요.", Toast.LENGTH_LONG).show()
            }
            else if (newPw.isBlank()) {
                Toast.makeText(this, "Password를 입력하세요.", Toast.LENGTH_LONG).show()
            }
            else if (nickname.isBlank()) {
                Toast.makeText(this, "Nickname을 입력하세요", Toast.LENGTH_LONG).show()
            }
            else if (checkNewPw != newPw) {
                Toast.makeText(this, "Password가 일치하지 않습니다. 다시 확인하세요.", Toast.LENGTH_LONG).show()
            }
            else {
                // 사용자 생성 및 닉네임 중복 확인
                auth.createUserWithEmailAndPassword(newEmail, newPw)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Firebase에서 제공하는 사용자 UID 가져오기
                            val user = auth.currentUser
                            val uid = user?.uid

                            // Firebase Realtime Database에서 닉네임 중복 확인
                            FirebaseRef.userInfo.child(nickname).addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        Toast.makeText(this@SignuppageActivity, "이미 사용 중인 닉네임입니다. 다른 닉네임을 선택하세요.", Toast.LENGTH_LONG).show()
                                    } else {
                                        // 닉네임 중복이 없으면 사용자 정보 저장
                                        val userInfo = UserInfo(newEmail, nickname, uid,"0")
                                        FirebaseRef.userInfo.child(nickname).setValue(userInfo)
                                        Toast.makeText(this@SignuppageActivity, "회원가입 성공", Toast.LENGTH_LONG).show()
                                        startActivity(intent)
                                        finish()
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    Log.d("SignuppageActivity", "Firebase Database Error: ${error.message}")
                                }
                            })
                        } else {
                            Toast.makeText(this@SignuppageActivity, "회원가입에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }
}
