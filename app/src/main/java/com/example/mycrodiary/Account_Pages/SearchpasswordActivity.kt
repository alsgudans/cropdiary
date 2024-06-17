package com.example.mycrodiary.Account_Pages

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivitySearchpasswordBinding
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class SearchpasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchpasswordBinding
    private lateinit var passwordemail: String
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.searchPasswordBtn.setOnClickListener() {
            passwordemail = binding.searchPasswordToid.text.toString()
            if (passwordemail.isBlank()) {
                Toast.makeText(this, "비밀번호를 찾을 이메일을 입력하세요.", Toast.LENGTH_LONG).show()
            } else {
                auth.sendPasswordResetEmail(passwordemail).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            this,
                            "${passwordemail}로 비밀번호 재설정 이메일을 보냈습니다.",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(this, "이메일 전송 실패. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}