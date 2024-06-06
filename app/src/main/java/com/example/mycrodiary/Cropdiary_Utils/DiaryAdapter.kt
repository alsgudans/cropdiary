package com.example.mycrodiary.Cropdiary_Utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListAdapter
import android.widget.TextView
import com.example.mycrodiary.Database_Utils.Cropinfo
import com.example.mycrodiary.Database_Utils.SensorDataInfo
import com.example.mycrodiary.R

class DiaryAdapter(val context: Context, private val diaryList: ArrayList<SensorDataInfo>) : BaseAdapter() {
    override fun getCount(): Int {
        return diaryList.size
    }

    override fun getItem(position: Int): Any {
        return diaryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.addeddiary, parent, false)
        val diary = getItem(position) as SensorDataInfo

        val flowTextView = view.findViewById<TextView>(R.id.added_water)
        val temperatureTextView = view.findViewById<TextView>(R.id.added_temperature)
        val humidityTextView = view.findViewById<TextView>(R.id.added_humidity)
        val illuminationTextView = view.findViewById<TextView>(R.id.added_illumination)

        flowTextView.text = diary.weight.toString()
        temperatureTextView.text = diary.temperature.toString()
        humidityTextView.text = diary.humidity.toString()
        illuminationTextView.text = diary.illumination.toString()

        return view
    }
}
