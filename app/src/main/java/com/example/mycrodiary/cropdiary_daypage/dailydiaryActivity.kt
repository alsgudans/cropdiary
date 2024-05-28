package com.example.mycrodiary.cropdiary_daypage

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityDailydiaryBinding

class dailydiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailydiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailydiaryBinding.inflate(layoutInflater) // 뷰바인딩 초기화
        setContentView(binding.root)

        // 버튼을 생성하고 LinearLayout에 추가
        for (i in 1..42) {
            val button = Button(this)
            button.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            button.text = "day $i"
            button.setBackgroundColor(resources.getColor(R.color.yello_green)) // 배경색 설정
            binding.buttonLayout.addView(button) // 뷰바인딩으로 레이아웃에 버튼 추가
        }
    }
}