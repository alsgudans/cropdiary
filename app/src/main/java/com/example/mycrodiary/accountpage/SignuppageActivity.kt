package com.example.mycrodiary.accountpage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.accountutil.FirebaseRef
import com.example.mycrodiary.accountutil.UserInfo
import com.example.mycrodiary.databinding.ActivitySignuppageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
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
            val newemail = binding.newEmail.text.toString()
            val newpw = binding.newPw.text.toString()
            val nickname = binding.newNickname.text.toString()
            val checknewpw = binding.checkNewPw.text.toString()

            if (newemail.isBlank()) {
                Toast.makeText(this, "Email을 입력하세요.", Toast.LENGTH_LONG).show()
            }
            else if (newpw.isBlank()) {
                Toast.makeText(this, "Password를 입력하세요.", Toast.LENGTH_LONG).show()
            }
            else if (nickname.isBlank()) {
                Toast.makeText(this, "Nickname을 입력하세요", Toast.LENGTH_LONG).show()
            }
            else if ( checknewpw != newpw) {
                Toast.makeText(this, "Password가 일치하지 않습니다. 다시 확인하세요.", Toast.LENGTH_LONG).show()
            }
            else{
                auth.createUserWithEmailAndPassword(newemail, newpw)
                    .addOnCompleteListener(this) { task ->
                        if(task.isSuccessful) {
                            val userInfo = UserInfo(newemail,nickname)
                            FirebaseRef.userInfo.child(nickname).setValue(userInfo)
                            Toast.makeText(this,"회원가입 성공", Toast.LENGTH_LONG).show()
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this, "Password는 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}

