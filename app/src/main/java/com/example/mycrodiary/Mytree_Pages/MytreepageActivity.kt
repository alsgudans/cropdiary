package com.example.mycrodiary.Mytree_Pages

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.Database_Utils.FirebaseRef
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityMytreepageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MytreepageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMytreepageBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMytreepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val currentuser = auth.currentUser
        val uid = currentuser?.uid.toString()

        binding.water.setOnClickListener {
            decrementPointsAndIncrementValue(uid, "water")
        }
        binding.Fertilizer.setOnClickListener {
            decrementPointsAndIncrementValue(uid, "Fertilizer")
        }
        binding.sun.setOnClickListener {
            decrementPointsAndIncrementValue(uid, "sun")
        }
        binding.love.setOnClickListener {
            decrementPointsAndIncrementValue(uid, "love")
        }
        binding.apple.setOnClickListener(){
            decrementPointsAndIncrementValue(uid,"apple")
        }
        binding.grape.setOnClickListener(){
            decrementPointsAndIncrementValue(uid,"grape")
        }


        updateTreeImage(uid)
    }

    private fun decrementPointsAndIncrementValue(uid: String, field: String) {
        val userRef = FirebaseRef.userInfo.child(uid)

        userRef.child("point").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentPointsString = snapshot.getValue(String::class.java)
                val currentPoints = currentPointsString?.toIntOrNull() ?: 0

                if (currentPoints >= 100) {
                    val newPoints = (currentPoints - 100).toString()
                    userRef.child("point").setValue(newPoints).addOnCompleteListener {
                        if (it.isSuccessful) {
                            incrementValue(uid, field)
                        } else {
                            // 포인트 차감 실패 시 처리
                            showErrorMessage()
                        }
                    }
                } else {
                    // 포인트 부족 메시지 표시
                    showInsufficientPointsMessage()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Log or handle the error
            }
        })
    }

    private fun incrementValue(uid: String, field: String) {
        val ref = FirebaseRef.treeInfo.child(uid).child(field)

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val currentValue = snapshot.getValue(Int::class.java) ?: 0
                ref.setValue(currentValue + 1).addOnCompleteListener {
                    if (it.isSuccessful) {
                        updateTreeImage(uid)  // Increment 후 이미지 업데이트
                    } else {
                        // 값 증가 실패 시 처리
                        showErrorMessage()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Log or handle the error
            }
        })
    }

    private fun updateTreeImage(uid: String) {
        val waterRef = FirebaseRef.treeInfo.child(uid).child("water")
        val fertilizerRef = FirebaseRef.treeInfo.child(uid).child("Fertilizer")
        val sunRef = FirebaseRef.treeInfo.child(uid).child("sun")
        val loveRef = FirebaseRef.treeInfo.child(uid).child("love")

        waterRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val water = snapshot.getValue(Int::class.java) ?: 0
                fertilizerRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val fertilizer = snapshot.getValue(Int::class.java) ?: 0
                        sunRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val sun = snapshot.getValue(Int::class.java) ?: 0
                                loveRef.addListenerForSingleValueEvent(object : ValueEventListener {
                                    override fun onDataChange(snapshot: DataSnapshot) {
                                        val love = snapshot.getValue(Int::class.java) ?: 0

                                        val minValue = listOf(water, fertilizer, sun, love).minOrNull() ?: 0
                                        val treeImageRes = when (minValue) {
                                            in 0..3 -> R.drawable.tree1
                                            in 4..6 -> R.drawable.tree2
                                            in 7..9 -> R.drawable.tree3
                                            in 10..Int.MAX_VALUE -> R.drawable.tree4
                                            else -> R.drawable.tree1 // 기본 이미지
                                        }

                                        binding.treei.setImageResource(treeImageRes)
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        // Log or handle the error
                                    }
                                })
                            }

                            override fun onCancelled(error: DatabaseError) {
                                // Log or handle the error
                            }
                        })
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Log or handle the error
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                // Log or handle the error
            }
        })
    }

    private fun showInsufficientPointsMessage() {
        // 포인트 부족 메시지 표시 코드
        Toast.makeText(this, "포인트가 부족합니다.", Toast.LENGTH_SHORT).show()
    }

    private fun showErrorMessage() {
        // 오류 메시지 표시 코드
        Toast.makeText(this, "오류가 발생했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
    }
}
