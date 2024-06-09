package com.example.mycrodiary.Setting_Pages

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityChangenicknameBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChangenicknameActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChangenicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangenicknameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app").reference

        val user = auth.currentUser
        val uid = user?.uid.toString()

        binding.changeNicknameBtn.setOnClickListener {
            val newNickname = binding.changeNickname.text.toString().trim()

            if (newNickname.isEmpty()) {
                Toast.makeText(this, "닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // 닉네임 중복 확인
            checkNicknameAvailability(newNickname, databaseReference) { isAvailable ->
                if (isAvailable) {
                    // 닉네임 업데이트
                    databaseReference.child("userInfo").child(uid).child("nickname").setValue(newNickname)
                        .addOnSuccessListener {
                            Toast.makeText(this, "닉네임이 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "닉네임 변경에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkNicknameAvailability(nickname: String, databaseReference: DatabaseReference, callback: (Boolean) -> Unit) {
        val userInfoRef = databaseReference.child("userInfo")

        userInfoRef.orderByChild("nickname").equalTo(nickname).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 닉네임이 이미 존재함
                    callback(false)
                } else {
                    // 닉네임이 존재하지 않음
                    callback(true)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터베이스 접근에 실패한 경우
                Toast.makeText(this@ChangenicknameActivity, "데이터베이스 접근 실패: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        })
    }
}
