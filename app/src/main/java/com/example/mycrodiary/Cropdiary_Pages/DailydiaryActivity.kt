package com.example.mycrodiary.Cropdiary_Pages

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mycrodiary.R
import com.example.mycrodiary.databinding.ActivityDailydiaryBinding

class DailydiaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDailydiaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDailydiaryBinding.inflate(layoutInflater) // 뷰바인딩 초기화
        setContentView(binding.root)

        // 버튼을 생성하고 LinearLayout에 추가
        val numColumns = 7
        val numRows = 6

        for (i in 1..numRows) {
            val rowLayout = LinearLayout(this)
            rowLayout.orientation = LinearLayout.HORIZONTAL
            rowLayout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            for (j in 1..numColumns) {
                val button = Button(this)
                button.layoutParams = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
                )
                button.text = "Day ${j + (i - 1) * numColumns}"
                button.setBackgroundColor(resources.getColor(R.color.yello_green))

                button.setOnClickListener {
                    val intent = Intent(this@DailydiaryActivity, DiarypageActivity::class.java)
                    startActivity(intent)
                }

                rowLayout.addView(button)
            }

            binding.buttonLayout.addView(rowLayout)
        }



        val cropName = intent.getStringExtra("cropname")
        val nickname = intent.getStringExtra("nickname")
        val date = intent.getStringExtra("date")

        val cropNameTextView = findViewById<TextView>(R.id.crop_name)
        val nicknameTextView = findViewById<TextView>(R.id.crop_nickname)
        val dateTextView = findViewById<TextView>(R.id.add_date)

        cropNameTextView.text = cropName
        nicknameTextView.text = nickname
        dateTextView.text = date
    }
}