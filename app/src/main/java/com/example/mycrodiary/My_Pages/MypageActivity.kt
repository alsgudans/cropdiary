package com.example.mycrodiary.My_Pages

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mycrodiary.Account_Pages.LoginpageActivity
import com.example.mycrodiary.databinding.ActivityMypageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class MypageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMypageBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var storageReference: StorageReference
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMypageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().reference
        val currentUser = auth.currentUser
        val uid = currentUser?.uid.toString()

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("이미지 업로드 중...")
        progressDialog.setCancelable(false)

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
                    val imageUrl = dataSnapshot.child("userimage").getValue(String::class.java)
                    // 가져온 데이터 UI에 표시
                    binding.nickname.text = nickname.toString()
                    binding.point.text = point.toString()
                    if (imageUrl != null) {
                        Glide.with(this@MypageActivity)
                            .load(imageUrl)
                            .into(binding.userimage)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("MypageActivity", "Failed to read user info", databaseError.toException())
                }
            })
        }

        binding.userimage.setOnClickListener {
            openGallery()
        }

        binding.logout.setOnClickListener {
            logout()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri: Uri? = data.data
            uploadImageToFirebase(imageUri)
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri?) {
        if (imageUri != null) {
            progressDialog.show()
            val ref = storageReference.child("images/${UUID.randomUUID()}")
            ref.putFile(imageUri)
                .addOnSuccessListener {
                    ref.downloadUrl.addOnSuccessListener { uri ->
                        saveImageUrlToDatabase(uri.toString())
                        Glide.with(this)
                            .load(imageUri)
                            .into(binding.userimage)
                        progressDialog.dismiss()
                    }
                }
                .addOnFailureListener {
                    Log.e("MypageActivity", "Image upload failed", it)
                    Toast.makeText(this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show()
                    progressDialog.dismiss()
                }
        }
    }

    private fun saveImageUrlToDatabase(imageUrl: String) {
        val uid = auth.currentUser?.uid.toString()
        val databaseReference = FirebaseDatabase.getInstance("https://project-my-crop-default-rtdb.asia-southeast1.firebasedatabase.app")
            .reference
            .child("userInfo")
            .child(uid)
        databaseReference.child("userimage").setValue(imageUrl)
    }

    private fun logout() {
        auth.signOut()

        // 로그인 정보 삭제
        val sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            remove("email")
            remove("password")
            apply()
        }

        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginpageActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}
