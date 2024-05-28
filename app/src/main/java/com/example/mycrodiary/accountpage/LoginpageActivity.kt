package com.example.mycrodiary.accountpage

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityLoginpageBinding
import com.example.mycrodiary.mainpage.MainpageActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginpageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.onStart()
        val binding = ActivityLoginpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val move_signinpage = Intent(this, SignuppageActivity::class.java)
        val move_mainpage = Intent(this, MainpageActivity::class.java)

        binding.signBtn.setOnClickListener(){
            startActivity(move_signinpage)
            finish()
        }

        binding.loginBtn.setOnClickListener{
            val email = binding.inputEmail.text.toString()
            val pw = binding.inputPw.text.toString()

            if (email.isBlank() || pw.isBlank()) {
                Toast.makeText(this, "Email 혹은 Password를 입력해주세요.",Toast.LENGTH_LONG).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        Toast.makeText(this,"로그인 성공",Toast.LENGTH_LONG).show()
                        startActivity(move_mainpage)
                        finish()
                    } else {
                        Log.d("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Email 혹은 Password를 확인하세요.", Toast.LENGTH_LONG,).show()
                    }
                }
            }
        }
    }
}