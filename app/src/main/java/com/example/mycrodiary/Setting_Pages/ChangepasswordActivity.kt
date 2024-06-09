package com.example.mycrodiary.Setting_Pages

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityChangepasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.EmailAuthProvider

class ChangepasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangepasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangepasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FirebaseAuth 인스턴스를 초기화합니다.
        auth = FirebaseAuth.getInstance()

        // 버튼 클릭 리스너를 설정합니다.
        binding.changePasswordBtn.setOnClickListener {
            val currentPassword = binding.nowPassword.text.toString()
            val newPassword = binding.changePassword.text.toString()
            val confirmPassword = binding.checkChangePassword.text.toString()

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "새 비밀번호와 비밀번호 확인이 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (newPassword.isEmpty() || currentPassword.isEmpty()) {
                Toast.makeText(this, "모든 필드를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            changePassword(currentPassword, newPassword)
        }
    }

    private fun changePassword(currentPassword: String, newPassword: String) {
        val user = auth.currentUser

        if (user != null && user.email != null) {
            // 현재 비밀번호로 사용자 재인증
            val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)
            user.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        // 새 비밀번호로 변경
                        user.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Toast.makeText(this, "비밀번호가 성공적으로 변경되었습니다.", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(this, "비밀번호 변경에 실패했습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "현재 비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
