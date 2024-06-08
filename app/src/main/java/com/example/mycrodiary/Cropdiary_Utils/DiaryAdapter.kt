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
        dayTextView.text = diary.day
        flowTextView.text = "${diary.weight} kg"
        temperatureTextView.text = "${diary.temperature} ℃"
        humidityTextView.text = "${diary.humidity} %"
        illuminationTextView.text = "${diary.illumination} lux"

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
