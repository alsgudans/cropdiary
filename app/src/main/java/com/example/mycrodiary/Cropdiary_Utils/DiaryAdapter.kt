package com.example.mycrodiary.Cropdiary_Utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import com.example.mycrodiary.Database_Utils.Cropinfo
import com.example.mycrodiary.Database_Utils.InputDataInfo
import com.example.mycrodiary.Database_Utils.SensorDataInfo
import com.example.mycrodiary.R

class DiaryAdapter(val context: Context, private val diaryList: ArrayList<InputDataInfo>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.addeddiary, parent, false)
        val diary = getItem(position) as InputDataInfo

        val dayTextView = view.findViewById<TextView>(R.id.added_day)
        val flowTextView = view.findViewById<TextView>(R.id.added_water)
        val temperatureTextView = view.findViewById<TextView>(R.id.added_temperature)
        val humidityTextView = view.findViewById<TextView>(R.id.added_humidity)
        val illuminationTextView = view.findViewById<TextView>(R.id.added_illumination)

        // 해당 TextView에 데이터 설정
        dayTextView.text = "작성일: ${diary.day} 일차"
        flowTextView.text = "물 잔여량: ${diary.weight}"
        temperatureTextView.text = "온도: ${diary.temperature}"
        humidityTextView.text = "습도: ${diary.humidity}"
        illuminationTextView.text = "광량: ${diary.illumination}"

        return view
    }

    override fun getCount(): Int {
        return diaryList.size
    }

    override fun getItem(position: Int): Any {
        return diaryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}
