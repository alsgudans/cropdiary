package com.example.mycrodiary

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.Account_Pages.LoginpageActivity
import com.example.mycrodiary.Main_Pages.MainpageActivity
import com.example.mycrodiary.databinding.ActivitySplashBinding
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 데이터 바인딩
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3초 대기 후 텍스트 보이기
        Handler(Looper.getMainLooper()).postDelayed({
            binding.loadingText.visibility = android.view.View.VISIBLE
        }, 3000) // 3초 후에 텍스트를 보이게 설정

        // 3초 대기 후 메인 액티비티로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser != null) {
                // 로그인 상태이면 메인 페이지로 이동
                startActivity(Intent(this, MainpageActivity::class.java))
            } else {
                // 로그인 상태가 아니면 로그인 페이지로 이동
                startActivity(Intent(this, LoginpageActivity::class.java))
            }
            finish()
        }, 6000) // 6초 후에 액티비티 전환 (3초 + 3초)
    }
}
