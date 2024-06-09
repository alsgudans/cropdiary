package com.example.mycrodiary.Account_Pages

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityLoginpageBinding
import com.example.mycrodiary.Main_Pages.MainpageActivity
import com.google.firebase.auth.FirebaseAuth

class LoginpageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginpageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginpageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val move_signinpage = Intent(this, SignuppageActivity::class.java)
        val move_mainpage = Intent(this, MainpageActivity::class.java)

        // Check for saved login credentials
        val sharedPref = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        val savedEmail = sharedPref.getString("email", null)
        val savedPassword = sharedPref.getString("password", null)

        if (auth.currentUser != null && savedEmail != null && savedPassword != null) {
            // Attempt to sign in with saved credentials
            auth.signInWithEmailAndPassword(savedEmail, savedPassword).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "signInWithEmail:success")
                    Toast.makeText(this, "자동 로그인 성공", Toast.LENGTH_LONG).show()
                    startActivity(move_mainpage)
                    finish()
                } else {
                    Log.d("TAG", "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "자동 로그인 실패, 다시 로그인해 주세요.", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.signBtn.setOnClickListener {
            startActivity(move_signinpage)
            finish()
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val pw = binding.inputPw.text.toString()
            val saveLogin = binding.saveId.isChecked

            if (email.isBlank() || pw.isBlank()) {
                Toast.makeText(this, "Email 혹은 Password를 입력해주세요.", Toast.LENGTH_LONG).show()
            } else {
                auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("TAG", "signInWithEmail:success")
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_LONG).show()

                        // Save login info if checkbox is checked
                        if (saveLogin) {
                            with(sharedPref.edit()) {
                                putString("email", email)
                                putString("password", pw)
                                apply()
                            }
                        }

                        startActivity(move_mainpage)
                        finish()
                    } else {
                        Log.d("TAG", "signInWithEmail:failure", task.exception)
                        Toast.makeText(this, "Email 혹은 Password를 확인하세요.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
