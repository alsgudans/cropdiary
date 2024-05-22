package com.example.mycrodiary.accountpage

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.accountutil.FirebaseRef
import com.example.mycrodiary.accountutil.UserInfo
import com.example.mycrodiary.databinding.ActivityCropdiarypageBinding
import com.example.mycrodiary.databinding.ActivitySignuppageBinding
import com.google.firebase.auth.FirebaseAuth

class SignuppageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignuppageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivitySignuppageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.makeAccount.setOnClickListener {
            val newemail = binding.newEmail.text.toString()
            val newpw = binding.newPw.text.toString()
            val nickname = binding.newNickname.text.toString()

            auth.createUserWithEmailAndPassword(newemail, newpw)
                    .addOnCompleteListener(this) { task ->
                    if(task.isSuccessful) {
                        val userInfo = UserInfo(newemail,nickname)
                        FirebaseRef.userInfo.child(nickname).setValue(userInfo)
                        Toast.makeText(this,"회원가입 성공", Toast.LENGTH_LONG).show()
                    }
                    else{
                        Toast.makeText(this, "Email 혹은 Password가 중복되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}

