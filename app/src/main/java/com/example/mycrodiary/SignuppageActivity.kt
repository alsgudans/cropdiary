package com.example.mycrodiary

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycrodiary.databinding.ActivitySignuppageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignuppageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val binding = ActivitySignuppageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.makeAccountBtn.setOnClickListener {
            val newemail = binding.newEmail.text.toString()
            val newpw = binding.newPw.text.toString()

            auth.createUserWithEmailAndPassword(newemail, newpw)
                    .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful) {
                        Toast.makeText(this, "회원가입 완료", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(this, "Email 혹은 Password가 중복되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}