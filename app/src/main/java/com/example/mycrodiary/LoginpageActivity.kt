package com.example.mycrodiary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycrodiary.databinding.ActivityLoginpageBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginpageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        auth = Firebase.auth

        val binding = ActivityLoginpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val move_signinpage = Intent(this,SigninpageActivity::class.java)

        binding.signBtn.setOnClickListener(){
            startActivity(move_signinpage)
        }

        binding.loginBtn.setOnClickListener(){
            var email = binding.inputEmail.text.toString()
            var pw = binding.inputPw.text.toString()

            if (email.isNullOrBlank() || pw.isNullOrBlank()) {
                Toast.makeText(this, "Email 혹은 Password를 입력해주세요.",Toast.LENGTH_LONG).show()
            }
            else{
                auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        Toast.makeText(this,"로그인 성공",Toast.LENGTH_LONG).show()
                    } else {
                        Log.d("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Email 혹은 Password를 확인하세요.", Toast.LENGTH_LONG,).show()
                    }
                }
            }
        }
    }
}