package com.example.mycrodiary.Setting_Pages

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.databinding.ActivityChangepasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class ChangepasswordActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityChangepasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.changePasswordBtn.setOnClickListener {
            val currentPassword = binding.nowPassword.text.toString()
            val newPassword = binding.changePassword.text.toString()
            val newPasswordConfirm = binding.checkChangedPassword.text.toString()

            if (newPassword != newPasswordConfirm) {
                showToast("새 비밀번호가 일치하지 않습니다.")
                return@setOnClickListener
            }

            val user = auth.currentUser
            if (user != null && user.email != null) {
                val credential = EmailAuthProvider.getCredential(user.email!!, currentPassword)

                // 현재 비밀번호 확인
                user.reauthenticate(credential).addOnCompleteListener { reAuthTask ->
                    if (reAuthTask.isSuccessful) {
                        // 새 비밀번호 설정
                        user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                showToast("비밀번호가 성공적으로 변경되었습니다.")
                                finish()
                            } else {
                                showToast("비밀번호 변경 중 오류가 발생했습니다.")
                            }
                        }
                    } else {
                        showToast("현재 비밀번호가 올바르지 않습니다.")
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
